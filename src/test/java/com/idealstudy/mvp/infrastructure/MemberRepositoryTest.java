package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.member.*;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Grade;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.enums.member.SchoolRegister;
import com.idealstudy.mvp.application.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    private final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void createAndFindStudent() {

        String uuid = UUID.randomUUID().toString();
        String password = "1234";
        String email = "teststudent@gmail.com";
        Role role = Role.ROLE_STUDENT;

        StudentDto dto = StudentDto.builder()
                .userId(uuid)
                .password(password)
                .email(email)
                .fromSocial(0)
                .role(role)
                .sex(Gender.MALE)
                .build();
        
        memberRepository.create(dto);

        StudentDto resultDto = memberRepository.findStudentById(uuid);
        Assertions.assertThat(resultDto.getEmail()).isEqualTo(email);
        Assertions.assertThat(resultDto.getRole()).isEqualTo(role);
    }

    @Test
    public void createAndFindParents() {

        String uuid = UUID.randomUUID().toString();
        String password = "1234";
        String email = "testparents@gmail.com";
        Role role = Role.ROLE_PARENTS;

        ParentsDto dto = ParentsDto.builder()
                .userId(uuid)
                .password(password)
                .email(email)
                .fromSocial(0)
                .role(role)
                .sex(Gender.MALE)
                .build();

        memberRepository.create(dto);

        ParentsDto resultDto = memberRepository.findParentsById(uuid);
        Assertions.assertThat(resultDto.getEmail()).isEqualTo(email);
        Assertions.assertThat(resultDto.getRole()).isEqualTo(role);
    }

    @Test
    @DisplayName("회원 데이터 조회 테스트")
    public void testFindById() {

        String teacherId = "98a10847-ad7e-11ef-8e5c-0242ac140002";

        MemberDto dto = memberRepository.findById(teacherId);

        Assertions.assertThat(dto.getRole()).isEqualTo(Role.ROLE_TEACHER);
    }

    @Test
    @DisplayName("회원 데이터 목록 조회 테스트")
    public void testFindMembers() {

        int page = 1;
        MemberPageResultDto dto = memberRepository.findMembers(page);

        Assertions.assertThat(dto.getSize()).isGreaterThan(4);
    }

    @Test
    @DisplayName("회원 데이터 수정 테스트")
    public void testUpdateMember() {

        String intro = "나는 강사입니다.";
        String teacherId = "98a10847-ad7e-11ef-8e5c-0242ac140002";
        
        MemberDto dto = MemberDto.builder()
                .userId(teacherId)
                .introduction(intro)
                .build();

        memberRepository.update(dto);

        MemberDto resultDto = memberRepository.findById(teacherId);
        Assertions.assertThat(resultDto.getUserId()).isEqualTo(teacherId);
        Assertions.assertThat(resultDto.getIntroduction()).isEqualTo(intro);
    }

    @Test
    public void testUpdateTeacher(){

        String univ = "내맘대로대학교";
        SchoolRegister status = SchoolRegister.LEAVE_OF_ABSENCE;
        String subject = "코딩";

        TeacherDto dto = TeacherDto.builder()
                .userId(TEACHER_ID)
                .univ(univ)
                .status(status)
                .subject(subject)
                .build();

        TeacherDto resultDto = memberRepository.update(dto);
        Assertions.assertThat(resultDto.getUniv()).isEqualTo(univ);
    }

    @Test
    public void testUpdateStudent(){

        String school = "안녕고등학교";
        Grade grade = Grade.H2;

        StudentDto dto = StudentDto.builder()
                .userId(STUDENT_ID)
                .school(school)
                .grade(grade)
                .build();

        StudentDto resultDto = memberRepository.update(dto);
        Assertions.assertThat(resultDto.getSchool()).isEqualTo(school);
        Assertions.assertThat(resultDto.getGrade()).isEqualTo(grade);
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    public void testDeleteMember() {

        String teacherId = "98a10847-ad7e-11ef-8e5c-0242ac140002";

        Assertions.assertThat(memberRepository.deleteById(teacherId)).isTrue();

        Assertions.assertThat(memberRepository.findById(teacherId).isDeleted()).isTrue();
    }


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
