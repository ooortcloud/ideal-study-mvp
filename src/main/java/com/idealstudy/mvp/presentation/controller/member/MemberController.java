package com.idealstudy.mvp.presentation.controller.member;

import com.idealstudy.mvp.application.service.member.EmailService;
import com.idealstudy.mvp.application.service.member.MemberService;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.dto.member.MemberPageResultDto;
import com.idealstudy.mvp.application.service.OfficialProfileService;
import com.idealstudy.mvp.enums.HttpResponse;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.dto.SignUpDto;
import com.idealstudy.mvp.presentation.dto.member.MemberResponseDto;
import com.idealstudy.mvp.presentation.dto.member.SignUpUserRequestDto;
import com.idealstudy.mvp.security.annotation.ForUser;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.util.HttpResponseUtil;
import com.idealstudy.mvp.util.TryCatchControllerTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final EmailService emailService;

    @Autowired
    private final OfficialProfileService officialProfileService;

    /*
     1. 로컬 파트(@ 앞 부분)는 영문자, 숫자, 그리고 일부 특수 문자(_+&*-)를 허용
     2. 도메인 파트(@ 뒷 부분)는 영문자, 숫자, 하이픈(-)을 허용
     3. 최상위 도메인(TLD)은 2~7자의 영문자로 제한
     */
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    @PostMapping("/users/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpUserRequestDto dto) {

        ResponseEntity<String> response = sendEmail(dto.getEmail(), dto.getRole());
        if(response != null)
            return response;

        return HttpResponseUtil.responseString(HttpResponse.SUCCESS_EMAIL);
    }

    @GetMapping("/users/email-authentication")
    public ResponseEntity<String> emailAuthentication(@RequestParam String emailToken,
                                                      HttpServletRequest request) {

        request.setAttribute("jwtPayload", JwtPayloadDto.builder()
                .sub(UUID.randomUUID().toString()).build());

        return TryCatchControllerTemplate.execute(() -> {

                Map<String, Object> returnMap = memberService.addMember(emailToken);
                SignUpDto signUpDto = (SignUpDto) returnMap.get("signUpDto");
                String password = (String) returnMap.get("password");
                String userId = (String) returnMap.get("userId");

                /// 마이페이지 자동 생성
                // 현재는 마이페이지에 필요한 모든 데이터가 member 테이블에 있어서 mypage 관련 테이블이 존재하지 않음.
                // 그래서 관련 로직이 없는 상태. (정확히는, 이 로직이 없어도 작동에는 문제 없는 상태)

                /// (강사에 한해서) 공식 페이지 자동 생성
                if(signUpDto.getRole() == Role.ROLE_TEACHER)
                    officialProfileService.create(userId);

                return password;
            }
        );
    }

    @ForUser
    @GetMapping("/api/users/{userId}")
    public ResponseEntity<MemberResponseDto> findMember(@PathVariable String userId, HttpServletRequest request) {

        return TryCatchControllerTemplate.execute(() -> {

            JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
            String tokenId = payload.getSub();

            return memberService.findById(userId, tokenId);
        });
    }

    @GetMapping("/users")
    public ResponseEntity<MemberPageResultDto> findMemberList(@RequestParam int page) {

        return TryCatchControllerTemplate.execute(() -> memberService.findMembers(page));
    }

    @ForUser
    @DeleteMapping("/api/users")
    public ResponseEntity<String> deleteMember(HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String userId = payload.getSub();

        boolean result = memberService.deleteMember(userId);

        if( !result)
            return new ResponseEntity<String>("회원탈퇴에 실패했습니다.", HttpStatusCode.valueOf(500));
        if(result)
            return new ResponseEntity<String>("성공적으로 회원탈퇴 되었습니다.", HttpStatusCode.valueOf(200));

        return null;
    }

    @ForUser
    @PatchMapping("/api/users/update")
    public ResponseEntity<MemberResponseDto> updateMember(HttpServletRequest request, @RequestBody MemberDto dto) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String userId = payload.getSub();

        return TryCatchControllerTemplate.execute(() ->
                memberService.updateMember(userId, dto.getPhoneAddress(), null));
    }

    private ResponseEntity<String> sendEmail(String email, Role role) {
        try{
            if(!isValidEmailPattern(email))
                return new ResponseEntity<String>("잘못된 이메일 양식입니다.", HttpStatusCode.valueOf(400));

            log.info("입력받은 이메일: "+ email);
            if(emailService.isEmailDuplication(email)) {
                return new ResponseEntity<String>("현재 등록 중이거나 이미 등록된 이메일입니다.",
                        HttpStatusCode.valueOf(400));
            }
            emailService.sendSignUpEmail(email, role);
        } catch (Exception e) {
            log.error(e.toString() + ":: " + e.getMessage());
            return new ResponseEntity<String>("failed to send email", HttpStatusCode.valueOf(500));
        }
        return null;
    }

    private boolean isValidEmailPattern(String email) {

        return emailPattern.matcher(email).matches();
    }
}
