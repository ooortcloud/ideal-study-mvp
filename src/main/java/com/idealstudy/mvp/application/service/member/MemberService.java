package com.idealstudy.mvp.application.service.member;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.member.*;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.infrastructure.RedisRepository;
import com.idealstudy.mvp.mapstruct.MemberMapper;
import com.idealstudy.mvp.presentation.dto.member.MemberResponseDto;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final RedisRepository redisRepository;

    // Repository에 포함시키면 순환 참조 문제 발생하여 불가능. 인코딩은 어플리케이션 계층에서 처리하기로 결정
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public String addMember(String email, String token, Role role) throws IllegalArgumentException {
        String savedToken = redisRepository.getToken(email);
        if( savedToken == null || !savedToken.equals(token))
            throw new IllegalArgumentException("유효한 토큰이 아님");

        String password = UUID.randomUUID().toString().split("-")[0];
        addMember(email, role, password);
        redisRepository.deleteToken(email);

        return password;
    }

    public MemberResponseDto findById(String userId) {

        return TryCatchServiceTemplate.execute(() -> {

            MemberDto dto = memberRepository.findById(userId);

            MemberResponseDto responseDto = MemberMapper.INSTANCE.toResponseDto(dto);

            // public private 에 따라 데이터 null처리 필요
            if(false) {
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

    public MemberResponseDto updateMember(String userId, String phoneAddress, String introduction, String profile) {

        return MemberMapper.INSTANCE.toResponseDto(memberRepository.update(userId, phoneAddress, introduction, profile));
    }

    public boolean deleteMember(String userId) {
        memberRepository.deleteById(userId);
        if(memberRepository.findById(userId) == null)
            return false;

        return true;
    }

    @Deprecated
    public void createDummies() {

        //addMember("student@gmail.com", Role.ROLE_STUDENT, "1234");
        //addMember("teacher@gmail.com", Role.ROLE_TEACHER, "1234");
        //addMember("parents@gmail.com", Role.ROLE_PARENTS, "1234");
        //addMember("admin@gmail.com", Role.ROLE_ADMIN, "1234");
        // addMember("badteacher@gmail.com", Role.ROLE_TEACHER, "1234");
        addMember("otherparents@gmail.com", Role.ROLE_PARENTS, "1234");
        addMember("otherstudent@gmail.com", Role.ROLE_STUDENT, "1234");
    }

    public boolean testPassword(String raw, String encoded) {
        return passwordEncoder.matches(raw, encoded);
    }

    private void addMember(String email, Role role, String password) {

        String encodedPassword = passwordEncoder.encode(password);

        if(role == Role.ROLE_TEACHER)
            memberRepository.create((TeacherDto) TeacherDto.builder()
                    .password(encodedPassword)
                    .email(email)
                    .fromSocial(0)
                    .role(role)
                    .sex(Gender.MALE)
                    .build());
        if(role == Role.ROLE_STUDENT)
            memberRepository.create((StudentDto) StudentDto.builder()
                    .password(encodedPassword)
                    .email(email)
                    .fromSocial(0)
                    .role(role)
                    .sex(Gender.MALE)
                    .build());
        if(role == Role.ROLE_PARENTS)
            memberRepository.create((ParentsDto) ParentsDto.builder()
                    .password(encodedPassword)
                    .email(email)
                    .fromSocial(0)
                    .role(role)
                    .sex(Gender.MALE)
                    .build());
        /*
        if(role == Role.ROLE_ADMIN)
            memberRepository.create((AdminDto) AdminDto.builder()
                    .userId(UUID.randomUUID().toString())
                    .password(encodedPassword)
                    .email(email)
                    .fromSocial(0)
                    .role(role)
                    .sex(Gender.MALE)
                    .build());

         */
    }
}
