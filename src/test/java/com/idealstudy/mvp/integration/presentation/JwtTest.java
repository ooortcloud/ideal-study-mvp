package com.idealstudy.mvp.integration.presentation;

import com.idealstudy.mvp.MvpApplication;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MvpApplication.class)
// @RequiredArgsConstructor
public class JwtTest {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtTest(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Test
    @Deprecated
    public void verifyTest() throws Exception {

        /*
        MemberDto dto = MemberDto.builder().userId("abcdefg").role(Role.ROLE_STUDENT).build();

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> {
            String token = jwtUtil.createToken(dto);
            jwtUtil.getPayloadFromToken(token.substring(7));
        });

         */
    }
}
