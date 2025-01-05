package com.idealstudy.mvp.unit;

import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.application.service.member.EmailService;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.EmailRepository;
import com.idealstudy.mvp.infrastructure.EmailSender;
import com.idealstudy.mvp.infrastructure.dto.SignUpDto;
import com.idealstudy.mvp.util.RandomValueGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private EmailSender emailSender;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RandomValueGenerator randomValueGenerator;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void sendSignUpEmail() throws Exception {

        String token = UUID.randomUUID().toString().split("-")[0];
        String email = "teststudent@gmail.com";
        Role role = Role.ROLE_STUDENT;
        Mockito.when(randomValueGenerator.createRandomValue()).thenReturn(token);

        SignUpDto dto = SignUpDto.builder()
                .email(email)
                .role(role)
                .build();

        Mockito.when(emailRepository.addToken(token, email, role))
                        .thenReturn(dto);

        Mockito.doNothing().when(emailSender).sendEmail(email, token);

        SignUpDto resultDto = emailService.sendSignUpEmail(email, role);
        Assertions.assertThat(resultDto.getEmail()).isEqualTo(email);
        Assertions.assertThat(resultDto.getRole()).isEqualTo(role);
    }

    @Test
    public void isEmailDuplication_whenDuplication() {

        String token = UUID.randomUUID().toString().split("-")[0];
        String email = "teststudent@gmail.com";
        Role role = Role.ROLE_STUDENT;

        SignUpDto signUpDto = SignUpDto.builder()
                .email(email)
                .role(role)
                .build();
        Mockito.when(emailRepository.getToken(token)).thenReturn(signUpDto);

        MemberDto memberDto = MemberDto.builder()
                .email(email)
                .role(role)
                .build();
        Mockito.when(memberRepository.findByEmail(signUpDto.getEmail()))
                .thenReturn(memberDto);

        Assertions.assertThat(emailService.isEmailDuplication(token)).isTrue();
    }

    @Test
    public void isEmailDuplication_whenUnique() {

        String token = UUID.randomUUID().toString().split("-")[0];
        String email = "teststudent@gmail.com";
        Role role = Role.ROLE_STUDENT;

        SignUpDto signUpDto = SignUpDto.builder()
                .email(email)
                .role(role)
                .build();
        Mockito.when(emailRepository.getToken(token)).thenReturn(signUpDto);

        Mockito.when(memberRepository.findByEmail(signUpDto.getEmail()))
                        .thenThrow(IllegalArgumentException.class);

        Assertions.assertThat(emailService.isEmailDuplication(token)).isFalse();
    }
}
