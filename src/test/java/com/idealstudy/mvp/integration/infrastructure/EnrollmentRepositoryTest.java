package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentPageResultDto;
import com.idealstudy.mvp.enums.classroom.EnrollmentStatus;
import com.idealstudy.mvp.application.repository.inclass.EnrollmentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class EnrollmentRepositoryTest {

    private final EnrollmentRepository enrollmentRepository;

    // 테스트 이외에 의존성 없음
    private final TestRepositoryUtil testRepositoryUtil;
    private Long autoIncrement;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    private static final Long EXIST_ID = 1L;

    private static final String TABLE_NAME = "enrollment";

    private static final String CUR_SCORE = "50점이오";

    private static final String TARGET_SCORE = "100점이요";

    private static final String REQUEST = "성적 팍팍 올리고 싶어요";

    private static final String DETERMINATION = "열심히 해볼게요";

    @Autowired
    public EnrollmentRepositoryTest(EnrollmentRepository enrollmentRepository, TestRepositoryUtil testRepositoryUtil) {
        this.enrollmentRepository = enrollmentRepository;
        this.testRepositoryUtil = testRepositoryUtil;
    }

    @BeforeEach
    public void getAutoIncrement() {
        autoIncrement = testRepositoryUtil.getAutoIncrement(TABLE_NAME);
    }

    @Test
    public void testEnroll() {

        String classroomId = CLASSROOM_ID;
        String applicant = PARENTS_ID;
        String student = STUDENT_ID;
        String curScore = CUR_SCORE;
        String targetScore = TARGET_SCORE;
        String request = REQUEST;
        String determination = DETERMINATION;

        EnrollmentDto dto = enrollmentRepository.enroll(classroomId, applicant, student, curScore, targetScore,
                request, determination);

        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(applicant);
        Assertions.assertThat(dto.getStudentId()).isEqualTo(student);
        Assertions.assertThat(dto.getCurScore()).isEqualTo(curScore);
        Assertions.assertThat(dto.getTargetScore()).isEqualTo(targetScore);
        Assertions.assertThat(dto.getRequest()).isEqualTo(request);
        Assertions.assertThat(dto.getDetermination()).isEqualTo(determination);
    }

    @Test
    public void testDrop() {

        createDummy();

        Long id = autoIncrement;
        String applicantId = PARENTS_ID;

        enrollmentRepository.drop(id, applicantId);
    }

    @Test
    public void testCheck() {

        createDummy();

        Long id = autoIncrement;

        EnrollmentDto dto = enrollmentRepository.check(id);
        Assertions.assertThat(dto.getStatus()).isEqualTo(EnrollmentStatus.CHECKED);
    }

    @Test
    public void testGetInfo() {

        createDummy();

        String classroomId = CLASSROOM_ID;
        String applicant = PARENTS_ID;
        String student = STUDENT_ID;
        String curScore = CUR_SCORE;
        String targetScore = TARGET_SCORE;
        String request = REQUEST;
        String determination = DETERMINATION;

        EnrollmentDto dto = enrollmentRepository.getInfo(autoIncrement);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(classroomId);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(applicant);
        Assertions.assertThat(dto.getStudentId()).isEqualTo(student);
        Assertions.assertThat(dto.getCurScore()).isEqualTo(curScore);
        Assertions.assertThat(dto.getTargetScore()).isEqualTo(targetScore);
        Assertions.assertThat(dto.getRequest()).isEqualTo(request);
        Assertions.assertThat(dto.getDetermination()).isEqualTo(determination);
    }

    @Test
    public void testGetList() {

        String classroomId = CLASSROOM_ID;
        int page = 1;

        createDummy();
        createDummy();

        EnrollmentPageResultDto dto = enrollmentRepository.getList(classroomId, page);
        Assertions.assertThat(dto.getDtoList().size()).isEqualTo(2);
    }

    @Test
    public void testRefuse() {

        createDummy();

        Long id = autoIncrement;

        enrollmentRepository.refuse(id);

        Assertions.assertThatThrownBy(() -> enrollmentRepository.getInfo(id));
    }

    @Test
    public void testBelong() {

        createDummy();

        String classroomId = CLASSROOM_ID;
        String studentId = STUDENT_ID;

        Assertions.assertThat(enrollmentRepository.checkAffiliated(classroomId, studentId))
                .isTrue();
    }

    private EnrollmentDto createDummy() {

        String classroomId = CLASSROOM_ID;
        String applicant = PARENTS_ID;
        String student = STUDENT_ID;
        String curScore = CUR_SCORE;
        String targetScore = TARGET_SCORE;
        String request = REQUEST;
        String determination = DETERMINATION;

        return enrollmentRepository.enroll(classroomId, applicant, student, curScore, targetScore,
                request, determination);
    }
}
