package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.member.*;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Grade;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.enums.member.SchoolRegister;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.util.RandomValueGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    private final MemberRepository memberRepository;

    private final HttpServletRequest request;

    private static final String DEFAULT_IMAGE = "logo.webp";

    @Autowired
    public MemberRepositoryTest(MemberRepository memberRepository, HttpServletRequest request) {
        this.memberRepository = memberRepository;
        this.request = request;
    }

    @BeforeEach
    public void setup() {

        // repository에서는 검증 로직이 존재하지 않음. 따라서 여기서는 랜덤값 아무거나 집어넣었다.
        String tokenId = RandomValueGenerator.createRandomValue();

        JwtPayloadDto dto = JwtPayloadDto.builder()
                .sub(tokenId)
                .build();

        request.setAttribute("jwtPayload", dto);
    }

    @Test
    public void createAndFindStudent() {

        StudentDto dto = createDummyStudent();

        StudentDto resultDto = memberRepository.findStudentById(dto.getUserId());
        Assertions.assertThat(dto.getUserId()).isEqualTo(resultDto.getUserId());
        Assertions.assertThat(dto.getPassword()).isEqualTo(resultDto.getPassword());
        Assertions.assertThat(dto.getName()).isEqualTo(resultDto.getName());
        Assertions.assertThat(dto.getEmail()).isEqualTo(resultDto.getEmail());
        Assertions.assertThat(dto.getFromSocial()).isEqualTo(resultDto.getFromSocial());
        Assertions.assertThat(dto.getRole()).isEqualTo(resultDto.getRole());
        Assertions.assertThat(dto.getSex()).isEqualTo(resultDto.getSex());
        Assertions.assertThat(dto.getLevel()).isEqualTo(resultDto.getLevel());
        Assertions.assertThat(dto.getReferralId()).isEqualTo(resultDto.getReferralId());
        Assertions.assertThat(dto.getInit()).isEqualTo(resultDto.getInit());
    }

    @Test
    public void createAndFindParents() {

        ParentsDto dto = createDummyParents();

        ParentsDto resultDto = memberRepository.findParentsById(dto.getUserId());
        Assertions.assertThat(dto.getUserId()).isEqualTo(resultDto.getUserId());
        Assertions.assertThat(dto.getPassword()).isEqualTo(resultDto.getPassword());
        Assertions.assertThat(dto.getName()).isEqualTo(resultDto.getName());
        Assertions.assertThat(dto.getEmail()).isEqualTo(resultDto.getEmail());
        Assertions.assertThat(dto.getFromSocial()).isEqualTo(resultDto.getFromSocial());
        Assertions.assertThat(dto.getRole()).isEqualTo(resultDto.getRole());
        Assertions.assertThat(dto.getSex()).isEqualTo(resultDto.getSex());
        Assertions.assertThat(dto.getLevel()).isEqualTo(resultDto.getLevel());
        Assertions.assertThat(dto.getReferralId()).isEqualTo(resultDto.getReferralId());
        Assertions.assertThat(dto.getInit()).isEqualTo(resultDto.getInit());
    }

    @Test
    public void createAndFindTeacher() {

        TeacherDto dto = createDummyTeacher();

        TeacherDto resultDto = memberRepository.findTeacherById(dto.getUserId());
        Assertions.assertThat(dto.getUserId()).isEqualTo(resultDto.getUserId());
        Assertions.assertThat(dto.getPassword()).isEqualTo(resultDto.getPassword());
        Assertions.assertThat(dto.getName()).isEqualTo(resultDto.getName());
        Assertions.assertThat(dto.getEmail()).isEqualTo(resultDto.getEmail());
        Assertions.assertThat(dto.getFromSocial()).isEqualTo(resultDto.getFromSocial());
        Assertions.assertThat(dto.getRole()).isEqualTo(resultDto.getRole());
        Assertions.assertThat(dto.getSex()).isEqualTo(resultDto.getSex());
        Assertions.assertThat(dto.getLevel()).isEqualTo(resultDto.getLevel());
        Assertions.assertThat(dto.getReferralId()).isEqualTo(resultDto.getReferralId());
        Assertions.assertThat(dto.getInit()).isEqualTo(resultDto.getInit());
    }

    @Test
    @DisplayName("회원 데이터 조회 테스트")
    public void testFindById() {

        // 어떤 Role이든 상관없이 조회할 수 있어야 한다.
        MemberDto dto = createDummyStudent();

        MemberDto findDto = memberRepository.findById(dto.getUserId());

        Assertions.assertThat(findDto.getUserId()).isEqualTo(dto.getUserId());
        Assertions.assertThat(findDto.getPassword()).isEqualTo(dto.getPassword());
        Assertions.assertThat(findDto.getName()).isEqualTo(dto.getName());
        Assertions.assertThat(findDto.getEmail()).isEqualTo(dto.getEmail());
        Assertions.assertThat(findDto.getFromSocial()).isEqualTo(dto.getFromSocial());
        Assertions.assertThat(findDto.getRole()).isEqualTo(dto.getRole());
        Assertions.assertThat(findDto.getSex()).isEqualTo(dto.getSex());
        Assertions.assertThat(findDto.getLevel()).isEqualTo(dto.getLevel());
        Assertions.assertThat(findDto.getReferralId()).isEqualTo(dto.getReferralId());
        Assertions.assertThat(findDto.getInit()).isEqualTo(dto.getInit());
    }

    @Test
    @DisplayName("회원 데이터 목록 조회 테스트")
    public void testFindMembers() {

        createDummyStudent();
        createDummyParents();
        createDummyTeacher();

        int page = 1;
        MemberPageResultDto dto = memberRepository.findMembers(page);

        Assertions.assertThat(dto.getDtoList().size()).isGreaterThan(3); // 더미 데이터 수에 따라 조정
        Assertions.assertThat(dto.getPageList().size()).isGreaterThanOrEqualTo(1);
        Assertions.assertThat(dto.getStartPage()).isEqualTo(1);
        Assertions.assertThat(dto.isHasPrev()).isFalse();
    }

    /// 각 Role 별 검색 기능은 추가 기능으로.
    // @Test
    @DisplayName("학생 조회 테스트")
    public void testStudentEmail() {

        String input = "student@gmail.com";

        MemberDto dto = memberRepository.findByEmail(input);
        Assertions.assertThat(dto)
                .isNotNull();
        Assertions.assertThat(dto.getRole()).isEqualTo(Role.ROLE_STUDENT);

    }

    // @Test
    @DisplayName("강사 조회 테스트")
    public void testTeacherEmail() {

        String input = "teacher@gmail.com";

        MemberDto dto = memberRepository.findByEmail(input);
        Assertions.assertThat(dto)
                .isNotNull();
        Assertions.assertThat(dto.getRole()).isEqualTo(Role.ROLE_TEACHER);

    }

    // @Test
    @DisplayName("학부모 조회 테스트")
    public void testParentsEmail() {

        String input = "parents@gmail.com";

        MemberDto dto = memberRepository.findByEmail(input);
        Assertions.assertThat(dto)
                .isNotNull();
        Assertions.assertThat(dto.getRole()).isEqualTo(Role.ROLE_PARENTS);

    }

    /*
    @Test
    @DisplayName("회원 데이터 수정 테스트")
    public void testUpdateMember() {

        MemberDto tempDto = createDummyTeacher();

        String newPhoneAddress = "010-2222-1111";
        String newProfileUri = "user-profile/test.png";  // 현재 profile 부분은 추가 기능으로 구현 안됨.

        MemberDto resultDto = memberRepository
                .update(tempDto.getUserId(), newPhoneAddress, newProfileUri);

        Assertions.assertThat(resultDto.getUserId()).isEqualTo(tempDto.getUserId());
        Assertions.assertThat(resultDto.getPhoneAddress()).isEqualTo(newPhoneAddress);
        Assertions.assertThat(resultDto.getName()).isEqualTo(tempDto.getName());
        Assertions.assertThat(resultDto.getIntroduction()).isEqualTo(tempDto.getIntroduction());
    }

     */

    @Test
    public void testUpdateTeacher(){

        TeacherDto tempDto = createDummyTeacher();

        String univ = "내맘대로대학교";
        SchoolRegister status = SchoolRegister.LEAVE_OF_ABSENCE;
        String subject = "코딩";

        TeacherDto resultDto = memberRepository.update(
                tempDto.getUserId(), null, null, null, univ, status, subject);
        Assertions.assertThat(resultDto.getUniv()).isEqualTo(univ);
        Assertions.assertThat(resultDto.getStatus()).isEqualTo(status);
        Assertions.assertThat(resultDto.getSubject()).isEqualTo(subject);
    }

    @Test
    public void testUpdateStudent(){

        StudentDto tempDto = createDummyStudent();

        String school = "안녕고등학교";
        Grade grade = Grade.H2;

        StudentDto resultDto = memberRepository.update(
                tempDto.getUserId(),null, null, null, school, grade);
        Assertions.assertThat(resultDto.getSchool()).isEqualTo(school);
        Assertions.assertThat(resultDto.getGrade()).isEqualTo(grade);
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    public void testDeleteMember() {

        ParentsDto tempDto = createDummyParents();

        memberRepository.deleteById(tempDto.getUserId());

        Assertions.assertThat(memberRepository.findById(tempDto.getUserId()).isDeleted())
                .isTrue();
    }

    // @Test
    @DisplayName("이메일 null 예외 테스트")
    public void testEmailNullException() {

        String input = "tester9999@gmail.com";

        Assertions.assertThatThrownBy(() -> memberRepository.findByEmail(input))
                .isInstanceOf(NullPointerException.class);
    }

    /*
    @Test
    @DisplayName("프로필 이미지 입력 테스트")
    public void testImage() {

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
    */


    private StudentDto createDummyStudent() {

        // 여기서는 암호화 로직은 생략하겠음. 어차피 서비스에서 암호화된 값을 그대로 넣는거라서. DB CRUD를 중점으로 보겠음.
        String userId = RandomValueGenerator.createRandomValue();
        String password = RandomValueGenerator.createRandomValue();
        String email = "teststudent@gmail.com";
        Integer fromSocial = 0;

        return memberRepository.createStudent(userId, password, email, fromSocial, DEFAULT_IMAGE);
    }

    private ParentsDto createDummyParents() {

        // 여기서는 암호화 로직은 생략하겠음. 어차피 서비스에서 암호화된 값을 그대로 넣는거라서. DB CRUD를 중점으로 보겠음.
        String userId = RandomValueGenerator.createRandomValue();
        String password = RandomValueGenerator.createRandomValue();
        String email = "testparents@gmail.com";
        Integer fromSocial = 0;

        return memberRepository.createParents(userId, password, email, fromSocial, DEFAULT_IMAGE);
    }

    private TeacherDto createDummyTeacher() {

        // 여기서는 암호화 로직은 생략하겠음. 어차피 서비스에서 암호화된 값을 그대로 넣는거라서. DB CRUD를 중점으로 보겠음.
        String userId = RandomValueGenerator.createRandomValue();
        String password = RandomValueGenerator.createRandomValue();
        String email = "testteacher@gmail.com";
        Integer fromSocial = 0;

        return memberRepository.createTeacher(userId, password, email, fromSocial, DEFAULT_IMAGE);
    }
}
