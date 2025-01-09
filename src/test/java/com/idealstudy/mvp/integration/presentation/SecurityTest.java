package com.idealstudy.mvp.integration.presentation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호 암호화 테스트")
    public void passwordTest() {

        String password = "1234";

        String encodedPassword = passwordEncoder.encode(password);

        boolean isTrue = passwordEncoder.matches(password, encodedPassword);

        Assertions.assertThat(isTrue).isTrue();
    }
}
