package com.idealstudy.mvp.application.service.member;

import com.idealstudy.mvp.application.dto.member.*;
import com.idealstudy.mvp.application.service.domain_service.ValidationManager;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.infrastructure.EmailRepository;
import com.idealstudy.mvp.infrastructure.dto.SignUpDto;
import com.idealstudy.mvp.mapstruct.MemberMapper;
import com.idealstudy.mvp.presentation.dto.member.MemberResponseDto;
import com.idealstudy.mvp.util.RandomValueGenerator;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final EmailRepository emailRepository;

    // Repository에 포함시키면 순환 참조 문제 발생하여 불가능. 인코딩은 어플리케이션 계층에서 처리하기로 결정
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final ValidationManager validationManager;

    @Autowired
    private final RandomValueGenerator randomValueGenerator;

    public Map<String, Object> addMember(String token){

        // 소셜 회원가입 시 메소드 처리를 어떻게 할지 고민해봐야 한다.
        Integer fromSocial = 0;

        return TryCatchServiceTemplate.execute(() -> {

            SignUpDto signUpDto = emailRepository.getToken(token);

            String password = randomValueGenerator.createRandomValue().split("-")[0];
            String userId = addMember(signUpDto.getEmail(), signUpDto.getRole(), password, fromSocial);
            emailRepository.deleteToken(token);

            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("password",password);
            returnMap.put("signUpDto", signUpDto);
            returnMap.put("userId", userId);
            return returnMap;

        }, null, DBErrorMsg.CREATE_ERROR);
    }

    public MemberResponseDto findById(String userId, String tokenId) {

        return TryCatchServiceTemplate.execute(() -> {

            MemberDto dto = memberRepository.findById(userId);

            MemberResponseDto responseDto = MemberMapper.INSTANCE.toResponseDto(dto);

            // 본인 자격 조회가 아닌 경우 private 정보는 숨긴다.
            if( !userId.equals(tokenId)) {
                responseDto.setEmail(null);
                responseDto.setUserId(null);
                responseDto.setPhoneAddress(null);
            }
            
            return responseDto;
        }, null, DBErrorMsg.SELECT_ERROR);

    }

    public MemberDto findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public MemberPageResultDto findMembers(int page) {

        return TryCatchServiceTemplate.execute(() -> {
            return memberRepository.findMembers(page);
        }, null,DBErrorMsg.SELECT_ERROR);
    }

    public MemberResponseDto updateMember(String userId, String phoneAddress, String profileUri) {

        return TryCatchServiceTemplate.execute(() -> {

            MemberDto memberDto = memberRepository.findById(userId);

            validationManager.validateIndividual(userId, memberDto.getUserId());

            return MemberMapper.INSTANCE.toResponseDto(memberRepository.update(userId, phoneAddress, profileUri));
        }, null, DBErrorMsg.UPDATE_ERROR);

    }

    public boolean deleteMember(String userId) {


        return TryCatchServiceTemplate.execute(() -> {

            MemberDto memberDto = memberRepository.findById(userId);

            validationManager.validateIndividual(userId, memberDto.getUserId());

            memberRepository.deleteById(userId);
            if(memberRepository.findById(userId) == null)
                return false;

            return true;
        }, null, DBErrorMsg.DELETE_ERROR);

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

        String userId = randomValueGenerator.createRandomValue();

        if(role == Role.ROLE_TEACHER)
            memberRepository.createTeacher(userId, encodedPassword, email, fromSocial);
        if(role == Role.ROLE_STUDENT)
            memberRepository.createStudent(userId, encodedPassword, email, fromSocial);
        if(role == Role.ROLE_PARENTS)
            memberRepository.createParents(userId, encodedPassword, email, fromSocial);

        return userId;
    }
}
