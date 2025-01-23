package com.idealstudy.mvp.application.service.member;

import com.idealstudy.mvp.application.domain_service.ProfileManager;
import com.idealstudy.mvp.application.dto.member.*;
import com.idealstudy.mvp.application.domain_service.ValidationManager;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Grade;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.enums.member.SchoolRegister;
import com.idealstudy.mvp.helper.WebUrlHelper;
import com.idealstudy.mvp.infrastructure.EmailRepository;
import com.idealstudy.mvp.infrastructure.dto.SignUpDto;
import com.idealstudy.mvp.mapstruct.MemberMapper;
import com.idealstudy.mvp.presentation.dto.member.StudentResponseDto;
import com.idealstudy.mvp.presentation.dto.member.TeacherResponseDto;
import com.idealstudy.mvp.util.RandomValueGenerator;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final EmailRepository emailRepository;

    // Repository에 포함시키면 순환 참조 문제 발생하여 불가능. 인코딩은 어플리케이션 계층에서 처리하기로 결정
    private final PasswordEncoder passwordEncoder;

    private final ValidationManager validationManager;

    private final ProfileManager profileManager;

    private final WebUrlHelper webUrlHelper;

    @Value("${spring.cloud.aws.cloudfront.domain-name}")
    private String cdnDomainName;

    private static final String DEFAULT_IMAGE = "user-profile/logo.webp";

    @Autowired
    public MemberService(MemberRepository memberRepository, EmailRepository emailRepository,
                         PasswordEncoder passwordEncoder, ValidationManager validationManager,
                         ProfileManager awsFileManager, WebUrlHelper webUrlHelper) {
        this.memberRepository = memberRepository;
        this.emailRepository = emailRepository;
        this.passwordEncoder = passwordEncoder;
        this.validationManager = validationManager;
        this.profileManager = awsFileManager;
        this.webUrlHelper = webUrlHelper;
    }

    public Map<String, Object> addMember(String token){

        ///TODO: 소셜 회원가입 시 메소드 처리를 어떻게 할지 고민해봐야 한다.
        Integer fromSocial = 0;

        SignUpDto signUpDto = emailRepository.getToken(token);

        String password = RandomValueGenerator.createRandomValue().split("-")[0];
        String userId = addMember(signUpDto.getEmail(), signUpDto.getRole(), password, fromSocial);
        emailRepository.deleteToken(token);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("password",password);
        returnMap.put("signUpDto", signUpDto);
        returnMap.put("userId", userId);
        return returnMap;
    }

    public StudentResponseDto findStudentById(String studentId, String tokenId) {

        StudentDto dto = memberRepository.findStudentById(studentId);

        StudentResponseDto responseDto = MemberMapper.INSTANCE.toResponseDto(dto);

        // 본인 자격 조회가 아닌 경우 private 정보는 숨긴다.
        if( tokenId != null && !studentId.equals(tokenId)) {
            responseDto.setEmail(null);
            responseDto.setUserId(null);
            responseDto.setPhoneAddress(null);
        }

        return responseDto;
    }

    public TeacherResponseDto findTeacherById(String teacherId, String tokenId) {

        TeacherDto dto = memberRepository.findTeacherById(teacherId);

        TeacherResponseDto responseDto = MemberMapper.INSTANCE.toResponseDto(dto);

        // 본인 자격 조회가 아닌 경우 private 정보는 숨긴다.
        if( tokenId != null && !teacherId.equals(tokenId)) {
            responseDto.setEmail(null);
            responseDto.setUserId(null);
            responseDto.setPhoneAddress(null);
        }

        return responseDto;
    }

    public MemberDto findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public MemberPageResultDto findMembers(int page) {

        return memberRepository.findMembers(page);
    }

    /*
    public MemberResponseDto updateMember(String userId, String phoneAddress, String profileUri) {

        return TryCatchServiceTemplate.execute(() -> {

            MemberDto memberDto = memberRepository.findById(userId);

            validationManager.validateIndividual(userId, memberDto.getUserId());

            return MemberMapper.INSTANCE.toResponseDto(memberRepository.update(userId, phoneAddress, profileUri));
        }, null, DBErrorMsg.UPDATE_ERROR);
    }

     */

    public StudentResponseDto updateStudent(String studentId, String name, String phoneAddress, Gender gender,
                                            String school, Grade grade, MultipartFile profile)
            throws IOException {

        MemberDto memberDto = memberRepository.findById(studentId);

        validationManager.validateIndividual(studentId, memberDto.getUserId());

        /// 프로필 사진 변경 시 S3 걸쳐서 처리해야 하므로 따로 처리
        if(profile != null)
            updateProfile(studentId, profile);

        StudentDto studentDto = memberRepository.update(studentId, name, phoneAddress, gender, school, grade);

        return MemberMapper.INSTANCE.toResponseDto(studentDto);
    }

    public TeacherResponseDto updateTeacher(String teacherId, String name, String phoneAddress, Gender gender,
                                            String univ, SchoolRegister status, String subject, MultipartFile profile)
            throws IOException {

        MemberDto memberDto = memberRepository.findById(teacherId);

        validationManager.validateIndividual(teacherId, memberDto.getUserId());

        /// 프로필 사진 변경 시 S3 걸쳐서 처리해야 하므로 따로 처리
        if(profile != null)
            updateProfile(teacherId, profile);

        TeacherDto teacherDto =memberRepository.update(teacherId, name, phoneAddress, gender, univ, status, subject);

        return MemberMapper.INSTANCE.toResponseDto(teacherDto);
    }

    public boolean deleteMember(String userId) {

        MemberDto memberDto = memberRepository.findById(userId);

        validationManager.validateIndividual(userId, memberDto.getUserId());

        memberRepository.deleteById(userId);
        if(memberRepository.findById(userId) == null)
            return false;

        return true;
    }

    public boolean isPasswordMatch(String raw, String encoded) {
        return passwordEncoder.matches(raw, encoded);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean isFirst(String userId) {

        MemberDto dto = memberRepository.findById(userId);

        return dto.getInit() == 1;
    }

    private String addMember(String email, Role role, String password, Integer fromSocial) {

        String encodedPassword = passwordEncoder.encode(password);

        String userId = RandomValueGenerator.createRandomValue();

        if(role == Role.ROLE_TEACHER)
            memberRepository.createTeacher(userId, encodedPassword, email, fromSocial, DEFAULT_IMAGE);
        if(role == Role.ROLE_STUDENT)
            memberRepository.createStudent(userId, encodedPassword, email, fromSocial, DEFAULT_IMAGE);
        if(role == Role.ROLE_PARENTS)
            memberRepository.createParents(userId, encodedPassword, email, fromSocial, DEFAULT_IMAGE);

        return userId;
    }

    private void updateProfile(String userId, MultipartFile profile) throws IOException {

        MemberDto memberDto = memberRepository.findById(userId);
        
        // 기본 이미지가 아닌 경우에 현재 프로필 이미지 제거
        String key = webUrlHelper.getKey(memberDto.getProfileUri());
        if( !key.equals(DEFAULT_IMAGE))
            profileManager.delete(key);

        String newKey = profileManager.upload(profile);

        memberRepository.updateProfile(userId, newKey);

    }
}
