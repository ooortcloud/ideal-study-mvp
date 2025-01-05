package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.EmailRepository;
import com.idealstudy.mvp.infrastructure.dto.SignUpDto;
import com.idealstudy.mvp.util.RandomValueGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class EmailRepositoryTest {

    private final RandomValueGenerator randomValueGenerator;

    private final EmailRepository emailRepository;

    @Autowired
    public EmailRepositoryTest(RandomValueGenerator randomValueGenerator, EmailRepository emailRepository) {
        this.randomValueGenerator = randomValueGenerator;
        this.emailRepository = emailRepository;
    }
    
    @Test
    @DisplayName("해당 이메일에 대한 객체 정보를 저장하고 조회한 뒤 삭제까지 하는 테스트")
    public void managementToken() {

        String token = randomValueGenerator.createRandomValue().split("-")[0];
        String email = "teststudent@gmail.com";
        Role role = Role.ROLE_STUDENT;

        SignUpDto createDto = emailRepository.addToken(token, email, role);
        Assertions.assertThat(createDto.getEmail()).isEqualTo(email);
        Assertions.assertThat(createDto.getRole()).isEqualTo(role);

        SignUpDto getDto = emailRepository.getToken(token);
        Assertions.assertThat(getDto.getEmail()).isEqualTo(email);
        Assertions.assertThat(getDto.getRole()).isEqualTo(role);

        emailRepository.deleteToken(token);

        Assertions.assertThatThrownBy(() -> emailRepository.getToken(token));
    }
}
