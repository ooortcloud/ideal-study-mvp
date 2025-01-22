package com.idealstudy.mvp.integration.presentation;

import com.idealstudy.mvp.MvpApplication;
import com.idealstudy.mvp.helper.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MvpApplication.class)
// @RequiredArgsConstructor
public class JwtTest {

    private final JwtHelper jwtUtil;

    @Autowired
    public JwtTest(JwtHelper jwtUtil) {
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
