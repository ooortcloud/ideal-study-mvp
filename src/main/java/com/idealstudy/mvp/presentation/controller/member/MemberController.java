package com.idealstudy.mvp.presentation.controller.member;

import com.idealstudy.mvp.application.service.member.EmailService;
import com.idealstudy.mvp.application.service.member.MemberService;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.dto.member.MemberPageResultDto;
import com.idealstudy.mvp.application.service.OfficialProfileService;
import com.idealstudy.mvp.enums.HttpResponse;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.presentation.dto.member.SignUpUserRequestDto;
import com.idealstudy.mvp.security.annotation.ForUser;
import com.idealstudy.mvp.util.HttpResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Deprecated
    // @GetMapping("/create-dummies")
    public void createDummy() {
        memberService.createDummies();
    }

    @PostMapping("/users/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpUserRequestDto dto) {

        ResponseEntity<String> response = sendEmail(dto.getEmail());
        if(response != null)
            return response;

        return HttpResponseUtil.responseString(HttpResponse.SUCCESS_EMAIL);
    }

    @GetMapping("/users/email-authentication")
    public ResponseEntity<String> emailAuthentication(@RequestParam String token, @RequestParam String email) {
        log.info("사용자 email: " + email);
        log.info("사용자 토큰값: " + token);

        String password = null;
        try {
            // TODO: 다른 권한에 대해서도 회원가입이 가능하도록 해야 함.
            password = memberService.addMember(email, token, Role.ROLE_STUDENT);

            // 마이페이지 자동 생성

            // (강사에 한해서) 공식 페이지 자동 생성
            if(true)
                officialProfileService.create();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatusCode.valueOf(400));
        }

        return new ResponseEntity<String>(password, HttpStatusCode.valueOf(200));
    }

    @ForUser
    @GetMapping("/api/users/{userId}")
    public ResponseEntity<MemberDto> findMember(@PathVariable String userId) {
        
        log.info("개인 정보 조회");
        MemberDto dto = memberService.findById(userId);
        log.info("당신의 정보: " + dto);
        if(dto != null)
            return new ResponseEntity<MemberDto>(dto, HttpStatusCode.valueOf(200));
        if(dto == null)
            return new ResponseEntity<MemberDto>(dto, HttpStatusCode.valueOf(404));

        return null;
    }

    // page number는 어떻게 표현할 생각?
    @GetMapping("/users")
    public ResponseEntity<MemberPageResultDto> findMemberList() {

        MemberPageResultDto dto = memberService.findMembers();
        if(dto != null)
            return new ResponseEntity<MemberPageResultDto>(dto, HttpStatusCode.valueOf(200));
        if(dto == null)
            return new ResponseEntity<MemberPageResultDto>(dto, HttpStatusCode.valueOf(404));

        return null;
    }

    @ForUser
    @DeleteMapping("/api/users/{userId}")
    public ResponseEntity<String> deleteMember(@PathVariable String userId) {

        boolean result = memberService.deleteMember(userId);

        if( !result)
            return new ResponseEntity<String>("회원탈퇴에 실패했습니다.", HttpStatusCode.valueOf(500));
        if(result)
            return new ResponseEntity<String>("성공적으로 회원탈퇴 되었습니다.", HttpStatusCode.valueOf(200));

        return null;
    }

    @ForUser
    @PatchMapping("/api/users/update/{userId}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable String userId, @RequestBody MemberDto dto) {
        dto.setUserId(userId);
        log.info("변경 희망하는 MemberDto: " + dto);
        MemberDto updateDto = memberService.updateMember(dto);

        if(updateDto != null)
            return new ResponseEntity<MemberDto>(updateDto, HttpStatusCode.valueOf(200));
        if(updateDto == null)
            return new ResponseEntity<MemberDto>(updateDto, HttpStatusCode.valueOf(404));

        return null;
    }

    private ResponseEntity<String> sendEmail(String email) {
        try{
            if(!isValidEmailPattern(email))
                return new ResponseEntity<String>("잘못된 이메일 양식입니다.", HttpStatusCode.valueOf(400));

            log.info("입력받은 이메일: "+ email);
            if(emailService.isEmailDuplication(email, memberService)) {
                return new ResponseEntity<String>("현재 등록 중이거나 이미 등록된 이메일입니다.",
                        HttpStatusCode.valueOf(400));
            }
            emailService.sendSignUpEmail(email);
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
