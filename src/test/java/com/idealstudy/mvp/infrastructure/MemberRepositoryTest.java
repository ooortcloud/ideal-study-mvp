package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.MemberEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Deprecated
    @Test
    @DisplayName("더미 데이터 삽입")
    public void setup() {

        IntStream.rangeClosed(1, 50).forEach(i -> {

            MemberDto dto = MemberDto.builder()
                    .password("abcd1234")
                    .phoneAddress("010-1234-1234")
                    .email("tester"+i+"@gmail.com")
                    .sex(Gender.MALE)
                    .referralId(UUID.randomUUID().toString())
                    .fromSocial(0)
                    .build();

            memberRepository.create(dto);
        });
    }

    @Test
    @DisplayName("데이터 조회 테스트")
    public void testFindById() {

        // System.out.println(memberRepository.findById(1L));
    }

    @Test
    @DisplayName("데이터 목록 조회 테스트")
    public void testFindMembers() {

        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(1)
                .size(10)
                .build();
        System.out.println(memberRepository.findMembers(pageRequestDto));
    }

    /*
    @Test
    @DisplayName("데이터 수정 테스트")
    public void testUpdateMember() {

        MemberDto dto = MemberDto.builder()
                .id(1L)
                .phoneAddress("010-5678-5678")
                .build();

        System.out.println(memberRepository.update(dto));
    }
    */

    /*
    @Test
    @DisplayName("데이터 삭제 테스트")
    public void testDeleteMember() {

        memberRepository.deleteById(1L);

        Assertions.assertThat(memberRepository.findById(1L)).isNull();
    }
    */

    @Test
    @DisplayName("관리자 조회 테스트")
    public void testAdminEmail() {

        String input = "admin@gmail.com";

        MemberDto dto = memberRepository.findByEmail(input);
        Assertions.assertThat(dto)
                .isNotNull();
        Assertions.assertThat(dto.getRole()).isEqualTo(Role.ROLE_ADMIN);

    }

    @Test
    @DisplayName("학생 조회 테스트")
    public void testStudentEmail() {

        String input = "student@gmail.com";

        MemberDto dto = memberRepository.findByEmail(input);
        Assertions.assertThat(dto)
                .isNotNull();
        Assertions.assertThat(dto.getRole()).isEqualTo(Role.ROLE_STUDENT);

    }

    @Test
    @DisplayName("강사 조회 테스트")
    public void testTeacherEmail() {

        String input = "teacher@gmail.com";

        MemberDto dto = memberRepository.findByEmail(input);
        Assertions.assertThat(dto)
                .isNotNull();
        Assertions.assertThat(dto.getRole()).isEqualTo(Role.ROLE_TEACHER);

    }

    @Test
    @DisplayName("학부모 조회 테스트")
    public void testParentsEmail() {

        String input = "parents@gmail.com";

        MemberDto dto = memberRepository.findByEmail(input);
        Assertions.assertThat(dto)
                .isNotNull();
        Assertions.assertThat(dto.getRole()).isEqualTo(Role.ROLE_PARENTS);

    }

    @Test
    @DisplayName("이메일 null 예외 테스트")
    public void testEmailNullException() {

        String input = "tester9999@gmail.com";

        Assertions.assertThatThrownBy(() -> memberRepository.findByEmail(input))
                .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    @DisplayName("프로필 이미지 입력 테스트")
    public void testImage() {

        /*
        MemberDto memberDto = MemberDto.builder()
                .
                        .build();

        memberRepository.create();
         */

    }

    @Test
    @DisplayName("잘못된 이메일 입력 예외 처리")
    public void testEmailException() {

    }

    @Test
    @DisplayName("잘못된 성별 입력 예외 처리")
    public void testGenderException() {

    }

    @Test
    @DisplayName("잘못된 비밀번호 입력 예외 처리")
    public void testPasswordException() {

    }

    @Test
    @DisplayName("잘못된 전화번호 입력 예외 처리")
    public void testPhoneAddressException() {

    }

    @Test
    @DisplayName("잘못된 추천인 코드 입력 예외 처리")
    public void testReferralIdException() {

    }


}
