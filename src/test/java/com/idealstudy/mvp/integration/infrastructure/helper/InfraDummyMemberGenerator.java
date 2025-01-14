package com.idealstudy.mvp.integration.infrastructure.helper;

import com.idealstudy.mvp.application.dto.member.ParentsDto;
import com.idealstudy.mvp.application.dto.member.StudentDto;
import com.idealstudy.mvp.application.dto.member.TeacherDto;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InfraDummyMemberGenerator {

    private final MemberRepository memberRepository;

    private final HttpServletRequest request;

    @Autowired
    public InfraDummyMemberGenerator(MemberRepository memberRepository, HttpServletRequest request) {
        this.memberRepository = memberRepository;
        this.request = request;
    }

    public StudentDto createDummyStudent(String userId) {

        // 여기서는 암호화 로직은 생략하겠음. 어차피 서비스에서 암호화된 값을 그대로 넣는거라서. DB CRUD를 중점으로 보겠음.
        String password = UUID.randomUUID().toString();
        String email = "teststudent@gmail.com";
        Integer fromSocial = 0;

        return memberRepository.createStudent(userId, password, email, fromSocial);
    }

    public ParentsDto createDummyParents(String userId) {

        // 여기서는 암호화 로직은 생략하겠음. 어차피 서비스에서 암호화된 값을 그대로 넣는거라서. DB CRUD를 중점으로 보겠음.
        String password = UUID.randomUUID().toString();
        String email = "testparents@gmail.com";
        Integer fromSocial = 0;

        return memberRepository.createParents(userId, password, email, fromSocial);
    }

    public TeacherDto createDummyTeacher(String userId) {

        // 여기서는 암호화 로직은 생략하겠음. 어차피 서비스에서 암호화된 값을 그대로 넣는거라서. DB CRUD를 중점으로 보겠음.
        String password = UUID.randomUUID().toString();
        String email = "testteacher@gmail.com";
        Integer fromSocial = 0;

        return memberRepository.createTeacher(userId, password, email, fromSocial);
    }

    public void setToken(String userId, Role role) {

        JwtPayloadDto payload = JwtPayloadDto.builder()
                .sub(userId)
                .role(role)
                .build();

        request.setAttribute("jwtPayload", payload);
    }
}
