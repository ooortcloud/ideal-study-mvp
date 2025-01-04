package com.idealstudy.mvp.unit.application;

import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.application.service.member.MemberService;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.EmailRepository;
import com.idealstudy.mvp.presentation.dto.member.MemberResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private EmailRepository redisRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String OTHER_STUDENT_ID = "e8445639-917a-4396-8aaa-4a68dd11e4c7";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    @Test
    public void testEncodePassword() {

        String raw = "1234";
        String encoded = "$2a$10$kdG9XoA8h0J7UirQ1xuUfuzVfa/BgGzZtEjmPc063.vrevHZfM6oK";

        boolean result = memberService.isPasswordMatch(raw, encoded);
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void findById_mine() {

        String userId = STUDENT_ID;
        String password = "1234";
        String encodedPassword = memberService.encodePassword(password);
        String name = "홍길동";
        String phoneAddress = "010-1234-1234";
        String email = "test@gmail.com";
        Gender sex = Gender.MALE;
        Integer level = 1;
        Role role = Role.ROLE_STUDENT;
        String introduction = "안녕하세요";
        Mockito.when(memberRepository.findById(userId)).thenReturn(
                MemberDto.builder()
                        .userId(userId)
                        .password(encodedPassword)
                        .name(name)
                        .phoneAddress(phoneAddress)
                        .email(email)
                        .sex(sex)
                        .level(level)
                        .role(role)
                        .introduction(introduction)
                        .fromSocial(0)
                        .init(1)
                        .deleted(0)
                        .build()
        );

        String tokenId = STUDENT_ID;
        MemberResponseDto dto = memberService.findById(userId, tokenId);

        Assertions.assertThat(dto.getUserId()).isEqualTo(userId);
        Assertions.assertThat(dto.getName()).isEqualTo(name);
        Assertions.assertThat(dto.getPhoneAddress()).isEqualTo(phoneAddress);
        Assertions.assertThat(dto.getEmail()).isEqualTo(email);
        Assertions.assertThat(dto.getSex()).isEqualTo(sex);
        Assertions.assertThat(dto.getLevel()).isEqualTo(level);
        Assertions.assertThat(dto.getRole()).isEqualTo(role);
        Assertions.assertThat(dto.getIntroduction()).isEqualTo(introduction);
    }

    @Test
    public void findById_notMine() {

        setup();

        String userId = STUDENT_ID;
        String tokenId = OTHER_STUDENT_ID;
        MemberResponseDto dto = memberService.findById(userId, tokenId);

        Assertions.assertThat(dto.getEmail()).isNull();
        Assertions.assertThat(dto.getPhoneAddress()).isNull();
        Assertions.assertThat(dto.getUserId()).isNull();

    }

    private void setup() {

        String userId = STUDENT_ID;
        String password = "1234";
        String encodedPassword = memberService.encodePassword(password);
        String name = "홍길동";
        String phoneAddress = "010-1234-1234";
        String email = "test@gmail.com";
        Gender sex = Gender.MALE;
        Integer level = 1;
        Role role = Role.ROLE_STUDENT;
        String introduction = "안녕하세요";
        Mockito.when(memberRepository.findById(userId)).thenReturn(
                MemberDto.builder()
                        .userId(userId)
                        .password(encodedPassword)
                        .name(name)
                        .phoneAddress(phoneAddress)
                        .email(email)
                        .sex(sex)
                        .level(level)
                        .role(role)
                        .introduction(introduction)
                        .fromSocial(0)
                        .init(1)
                        .deleted(0)
                        .build()
        );
    }
}
