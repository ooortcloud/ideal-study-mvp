package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.dto.member.StudentDto;
import com.idealstudy.mvp.integration.infrastructure.helper.InfraDummyClassGenerator;
import com.idealstudy.mvp.integration.infrastructure.helper.InfraDummyMemberGenerator;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentDto;
import com.idealstudy.mvp.enums.classroom.EnrollmentStatus;
import com.idealstudy.mvp.application.repository.inclass.EnrollmentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@SpringBootTest
@Transactional
public class EnrollmentRepositoryTest {

    private final EnrollmentRepository enrollmentRepository;

    private final InfraDummyMemberGenerator dummyMemberGenerator;

    private final InfraDummyClassGenerator dummyClassGenerator;

    private static final String CUR_SCORE = "50점이오";

    private static final String TARGET_SCORE = "100점이요";

    private static final String REQUEST = "성적 팍팍 올리고 싶어요";

    private static final String DETERMINATION = "열심히 해볼게요";

    @Autowired
    public EnrollmentRepositoryTest(EnrollmentRepository enrollmentRepository, InfraDummyMemberGenerator dummyMemberGenerator, InfraDummyClassGenerator dummyClassGenerator) {
        this.enrollmentRepository = enrollmentRepository;
        this.dummyMemberGenerator = dummyMemberGenerator;
        this.dummyClassGenerator = dummyClassGenerator;
    }

    @Test
    public void createAndSelectAndUpdateAndDelete_applicantIsStudent() {

        Map<String, Object> dummyMap = dummyClassGenerator.createDummy();
        ClassroomResponseDto dummyClassroom = (ClassroomResponseDto) dummyMap.get("classroomResponseDto");

        String studentId = UUID.randomUUID().toString();
        StudentDto dummyStudent = dummyMemberGenerator.createDummyStudent(studentId);

        EnrollmentDto enrollmentDto = enrollmentRepository
                .request(dummyClassroom.getId(), studentId, CUR_SCORE, TARGET_SCORE, REQUEST, DETERMINATION);

        EnrollmentDto findDto = enrollmentRepository.getInfo(enrollmentDto.getEnrollmentId());

        Assertions.assertThat(enrollmentDto.getEnrollmentId()).isEqualTo(findDto.getEnrollmentId());
        Assertions.assertThat(enrollmentDto.getClassroomId()).isEqualTo(findDto.getClassroomId());
        Assertions.assertThat(enrollmentDto.getCreatedBy()).isEqualTo(findDto.getCreatedBy());
        Assertions.assertThat(enrollmentDto.getStudentId()).isEqualTo(findDto.getStudentId());
        Assertions.assertThat(enrollmentDto.getCurScore()).isEqualTo(findDto.getCurScore());
        Assertions.assertThat(enrollmentDto.getTargetScore()).isEqualTo(findDto.getTargetScore());
        Assertions.assertThat(enrollmentDto.getRequest()).isEqualTo(findDto.getRequest());
        Assertions.assertThat(enrollmentDto.getDetermination()).isEqualTo(findDto.getDetermination());

        String newCurScore = "90점!";
        String newTargetScore = "100점!";
        String newRequest = "update";
        String newDetermination = "update~";

        EnrollmentDto updateDto = enrollmentRepository
                .update(enrollmentDto.getEnrollmentId(), newCurScore, newTargetScore, newRequest, newDetermination);

        Assertions.assertThat(updateDto.getEnrollmentId()).isEqualTo(enrollmentDto.getEnrollmentId());
        Assertions.assertThat(updateDto.getClassroomId()).isEqualTo(enrollmentDto.getClassroomId());
        Assertions.assertThat(updateDto.getCreatedBy()).isEqualTo(enrollmentDto.getCreatedBy());
        Assertions.assertThat(updateDto.getStudentId()).isEqualTo(enrollmentDto.getStudentId());
        Assertions.assertThat(updateDto.getCurScore()).isEqualTo(newCurScore);
        Assertions.assertThat(updateDto.getTargetScore()).isEqualTo(newTargetScore);
        Assertions.assertThat(updateDto.getRequest()).isEqualTo(newRequest);
        Assertions.assertThat(updateDto.getDetermination()).isEqualTo(newDetermination);

        enrollmentRepository.drop(enrollmentDto.getEnrollmentId(), studentId);

        Assertions.assertThatThrownBy(() -> enrollmentRepository.getInfo(enrollmentDto.getEnrollmentId()));
    }

    /// 나중에 학부모 로직 설계되면 테스트 진행할 것
    // @Test
    public void createAndSelectAndUpdateAndDelete_applicantIsParents() {

        // enrollmentRepository.enroll()
    }

    @Test
    public void createAndAcceptAndDrop() {

        Map<String, Object> dummyMap = dummyClassGenerator.createDummy();
        ClassroomResponseDto dummyClassroom = (ClassroomResponseDto) dummyMap.get("classroomResponseDto");

        String studentId = UUID.randomUUID().toString();
        StudentDto dummyStudent = dummyMemberGenerator.createDummyStudent(studentId);

        EnrollmentDto enrollmentDto = enrollmentRepository
                .request(dummyClassroom.getId(), studentId, CUR_SCORE, TARGET_SCORE, REQUEST, DETERMINATION);

        EnrollmentDto acceptEnrollment = enrollmentRepository.accept(enrollmentDto.getEnrollmentId());
        Assertions.assertThat(acceptEnrollment.getStatus()).isEqualTo(EnrollmentStatus.CHECKED);

        enrollmentRepository.drop(enrollmentDto.getEnrollmentId(), studentId);

        Assertions.assertThatThrownBy(() -> enrollmentRepository.getInfo(enrollmentDto.getEnrollmentId()));
    }
}
