package com.idealstudy.mvp.presentation.controller.member;

import com.idealstudy.mvp.application.service.member.EmailService;
import com.idealstudy.mvp.application.service.member.MemberService;
import com.idealstudy.mvp.application.dto.member.MemberPageResultDto;
import com.idealstudy.mvp.application.service.OfficialProfileService;
import com.idealstudy.mvp.enums.HttpResponse;
import com.idealstudy.mvp.enums.error.UserErrorMsg;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.error.CustomException;
import com.idealstudy.mvp.infrastructure.dto.SignUpDto;
import com.idealstudy.mvp.presentation.dto.member.*;
import com.idealstudy.mvp.security.annotation.ForStudent;
import com.idealstudy.mvp.security.annotation.ForTeacher;
import com.idealstudy.mvp.security.annotation.ForUser;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Operation(summary = "회원가입", description = "사용자가 회원가입을 신청한 이메일로 등록 메일을 보냅니다.")
    @PostMapping("/users/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpUserRequestDto dto) throws Exception {
        
        /// TODO: 이미 존재하는 이메일인지 체크하고, 중복 이메일이면 요청 거부 로직 필요

        sendEmail(dto.getEmail(), dto.getRole());

        return ResponseEntity.ok(HttpResponse.SUCCESS.getMsg());
    }

    @Operation(summary = "이메일 인증", description = "사용자가 발급받은 이메일 경로를 입력했을 때 접속되는 엔드포인트입니다.")
    @GetMapping("/users/email-authentication")
    public ResponseEntity<String> emailAuthentication(@RequestParam String emailToken,
                                                      HttpServletRequest request) {

        request.setAttribute("jwtPayload", JwtPayloadDto.builder()
                .sub(UUID.randomUUID().toString()).build());

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

        return ResponseEntity.ok(password);
    }

    @Operation(summary = "학생 정보 조회")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<StudentResponseDto> findStudent(@PathVariable String studentId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String userId = null;
        if(payload != null)
            userId = payload.getSub();

        return ResponseEntity.ok(memberService.findStudentById(studentId, userId));
    }

    @Operation(summary = "강사 정보 조회")
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<TeacherResponseDto> findTeacher(@PathVariable String teacherId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");

        String userId = null;
        if(payload != null)
            userId = payload.getSub();

        return ResponseEntity.ok( memberService.findTeacherById(teacherId, userId));
    }

    /*
    @Operation(summary = "학부모 정보 조회")
    @ForUser
    @GetMapping("/api/parents/{studentId}")
    public ResponseEntity<MemberResponseDto> findParents(@PathVariable String studentId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String tokenId = payload.getSub();


        return ResponseEntity.ok(memberService.findById(studentId, tokenId));
    }

     */

    @Operation(summary = "회원 목록 조회")
    @GetMapping("/users")
    public ResponseEntity<MemberPageResultDto> findMemberList(@RequestParam Integer page) {

        return ResponseEntity.ok(memberService.findMembers(page));
    }

    @Operation(summary = "회원 정보 삭제")
    @ForUser
    @DeleteMapping("/api/users")
    public ResponseEntity<String> deleteMember(HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String userId = payload.getSub();

        boolean result = memberService.deleteMember(userId);

        if( !result)
            return new ResponseEntity<String>(HttpResponse.FAILED.getMsg(), HttpResponse.FAILED.getHttpStatus());
        if(result)
            return new ResponseEntity<String>(HttpResponse.SUCCESS.getMsg(), HttpResponse.SUCCESS.getHttpStatus());

        return null;
    }

    /*
    @ForUser
    @PatchMapping("/api/users/update")
    public ResponseEntity<MemberResponseDto> updateMember(HttpServletRequest request, @RequestBody MemberDto dto) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String userId = payload.getSub();

        return ResponseEntity.ok(memberService.updateMember(userId, dto.getPhoneAddress(), null));
    }

     */
    @Operation(summary = "학생 정보 수정", description = "이미지 정보를 포함하여 데이터를 입력받습니다.")
    @ForStudent
    @PatchMapping(value = "/api/student/update", consumes = "multipart/form-data")
    public ResponseEntity<StudentResponseDto> updateStudent(HttpServletRequest request,
                                                           @RequestPart("dto") StudentRequestDto dto,
                                                           @RequestPart(value = "profile", required = false) MultipartFile profile)
            throws IOException {

        if(!isImage(profile))
            throw new CustomException(UserErrorMsg.INVALID_CONTENT_TYPE);

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String studentId = payload.getSub();

        return ResponseEntity.ok(
                memberService.updateStudent(studentId, dto.getName(), dto.getPhoneAddress(), dto.getSex(),
                        dto.getSchool(), dto.getGrade(), profile));
    }

    @Operation(summary = "학생 정보 수정(JSON)", description = "이미지 정보를 제외한 나머지 정보들에 대해 입력받습니다.")
    @ForStudent
    @PatchMapping(value = "/api/student/update/json")
    public ResponseEntity<StudentResponseDto> updateStudentOnlyJson(HttpServletRequest request,
                                                            @RequestBody StudentRequestDto dto)
            throws IOException {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String studentId = payload.getSub();

        return ResponseEntity.ok(
                memberService.updateStudent(studentId, dto.getName(), dto.getPhoneAddress(), dto.getSex(),
                        dto.getSchool(), dto.getGrade(), null));
    }

    @Operation(summary = "강사 정보 수정")
    @ForTeacher
    @PatchMapping(value = "/api/teacher/update", consumes = "multipart/form-data")
    public ResponseEntity<TeacherResponseDto> updateTeacher(HttpServletRequest request,
                                                           @RequestPart("dto") TeacherRequestDto dto,
                                                           @RequestPart(value = "profile", required = false) MultipartFile profile)
            throws IOException {

        if(!isImage(profile))
            throw new CustomException(UserErrorMsg.INVALID_CONTENT_TYPE);

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return ResponseEntity.ok(
                memberService.updateTeacher(teacherId,  dto.getName(), dto.getPhoneAddress(), dto.getSex(),
                        dto.getUniv(), dto.getStatus(), dto.getSubject(), profile));
    }

    @Operation(summary = "강사 정보 수정(JSON)", description = "이미지 정보를 제외한 나머지 정보들에 대해 입력받습니다.")
    @ForTeacher
    @PatchMapping("/api/teacher/update/json")
    public ResponseEntity<TeacherResponseDto> updateTeacher(HttpServletRequest request,
                                                            @RequestBody TeacherRequestDto dto)
            throws IOException {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return ResponseEntity.ok(
                memberService.updateTeacher(teacherId,  dto.getName(), dto.getPhoneAddress(), dto.getSex(),
                        dto.getUniv(), dto.getStatus(), dto.getSubject(), null));
    }

    private void sendEmail(String email, Role role) throws Exception {

        if(!isValidEmailPattern(email))
            throw new CustomException(UserErrorMsg.ILLEGAL_EMAIL);

        log.info("입력받은 이메일: "+ email);
        if(emailService.isEmailDuplication(email))
            throw new CustomException(UserErrorMsg.DUPLICATION_EMAIL_REQUEST);

        emailService.sendSignUpEmail(email, role);
    }

    private boolean isValidEmailPattern(String email) {

        return emailPattern.matcher(email).matches();
    }

    private boolean isImage(MultipartFile file) {

        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
