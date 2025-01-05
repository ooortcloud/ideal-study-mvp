package com.idealstudy.mvp.unit;

import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.dto.member.MemberListDto;
import com.idealstudy.mvp.application.dto.member.MemberPageResultDto;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.application.service.member.MemberService;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.EmailRepository;
import com.idealstudy.mvp.infrastructure.dto.SignUpDto;
import com.idealstudy.mvp.presentation.dto.member.MemberResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private EmailRepository emailRepository;

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

        setFindById();

        String userId = STUDENT_ID;
        String tokenId = OTHER_STUDENT_ID;
        MemberResponseDto dto = memberService.findById(userId, tokenId);

        Assertions.assertThat(dto.getEmail()).isNull();
        Assertions.assertThat(dto.getPhoneAddress()).isNull();
        Assertions.assertThat(dto.getUserId()).isNull();

    }

    @Test
    public void addMember_teacher() {

        String email = "testteacher@gmail.com";
        Role role = Role.ROLE_TEACHER;

        String token = setEmailRepository(email, role);

        Assertions.assertThatCode(() -> memberService.addMember(token))
                .doesNotThrowAnyException();

    }

    @Test
    public void addMember_student() {

        String email = "teststudent@gmail.com";
        Role role = Role.ROLE_TEACHER;

        String token = setEmailRepository(email, role);

        Assertions.assertThatCode(() -> memberService.addMember(token))
                .doesNotThrowAnyException();
    }

    @Test
    public void addMember_parents() {

        String email = "testparents@gmail.com";
        Role role = Role.ROLE_TEACHER;

        String token = setEmailRepository(email, role);

        Assertions.assertThatCode(() -> memberService.addMember(token))
                .doesNotThrowAnyException();
    }

    @Test
    public void findMembers() {

        MemberPageResultDto resultDto = new MemberPageResultDto();
        List<MemberListDto> dtoList = new ArrayList<>();
        dtoList.add(MemberListDto.builder()
                        .userId("asdf-asdf-asdf")
                        .name("김철수")
                        .build());
        dtoList.add(MemberListDto.builder()
                .userId("fda-fdsa-fdsa")
                .name("장철수")
                .build());
        resultDto.setDtoList(dtoList);
        resultDto.setPage(1);
        resultDto.setSize(2);
        resultDto.setHasPrev(false);
        resultDto.setHasNext(false);
        resultDto.setTotalPage(1);
        resultDto.setStartPage(1);
        resultDto.setEndPage(1);
        List<Integer> pageList = new ArrayList<>();
        pageList.add(1);
        resultDto.setPageList(pageList);

        int page = 1;

        Mockito.when(memberRepository.findMembers(page)).thenReturn(resultDto);

        MemberPageResultDto dto = memberService.findMembers(page);
        Assertions.assertThat(dto.getDtoList()).isEqualTo(dtoList);
        Assertions.assertThat(dto.getPage()).isEqualTo(1);
        Assertions.assertThat(dto.getSize()).isEqualTo(2);
        Assertions.assertThat(dto.isHasPrev()).isFalse();
        Assertions.assertThat(dto.isHasNext()).isFalse();
        Assertions.assertThat(dto.getTotalPage()).isEqualTo(1);
        Assertions.assertThat(dto.getStartPage()).isEqualTo(1);
        Assertions.assertThat(dto.getEndPage()).isEqualTo(1);
        Assertions.assertThat(dto.getPageList()).isEqualTo(pageList);
    }

    @Test
    public void update() {

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

        MemberDto findDto = MemberDto.builder()
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
                .build();

        String newPhoneAddress = "010-2222-3333";
        String newIntroduction = "반갑다.";
        byte[] profile = null;
        String profileUri = "";

        findDto.setPhoneAddress(newPhoneAddress);
        findDto.setIntroduction(newIntroduction);
        findDto.setProfile(profile);

        Mockito.when(memberRepository.update(findDto.getUserId(), newPhoneAddress, newIntroduction, profileUri))
                .thenReturn(findDto);

        MemberResponseDto dto = memberService.updateMember(findDto.getUserId(), newPhoneAddress, newIntroduction, profileUri);
        Assertions.assertThat(findDto.getPhoneAddress()).isEqualTo(newPhoneAddress);
        Assertions.assertThat(findDto.getIntroduction()).isEqualTo(newIntroduction);
    }

    private MemberDto setFindById() {

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

        MemberDto dto = MemberDto.builder()
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
                .build();

        Mockito.when(memberRepository.findById(userId)).thenReturn(dto);

        return dto;
    }

    private String setEmailRepository(String email, Role role) {

        String token = UUID.randomUUID().toString();

        Mockito.when(emailRepository.getToken(token)).thenReturn(
                SignUpDto.builder()
                        .email(email)
                        .role(role)
                        .build());

        // 단위 테스트에서 테스트 불가
        Mockito.when(emailRepository.deleteToken(token)).thenReturn(Boolean.TRUE);

        return token;
    }
}
