package com.idealstudy.mvp.application.exam;

import com.idealstudy.mvp.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamFeedbackDetailResponse;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamListResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamPageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionPageResultDto;
import com.idealstudy.mvp.application.service.classroom.inclass.exam.ExamCommonService;
import com.idealstudy.mvp.application.service.classroom.inclass.exam.ExamStudentService;
import com.idealstudy.mvp.application.service.classroom.inclass.exam.ExamTeacherService;
import com.idealstudy.mvp.enums.classroom.AssessmentStatus;
import com.idealstudy.mvp.enums.classroom.SubmissionStatus;
import com.idealstudy.mvp.presentation.dto.classroom.SubmissionResponseDto;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import jakarta.servlet.http.HttpServletRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class ExamCommonServiceTest {

    private final ExamCommonService examCommonService;
    
    // 더미 데이터 생성 시 필요
    private final ExamTeacherService examTeacherService;
    
    // 시험 임시 제출 시 필요
    private final ExamStudentService examStudentService;

    // 테스트 이외에 의존성 없음
    private final TestRepositoryUtil testRepositoryUtil;
    private Long assessmentAutoIncrement;
    private Long submissionAutoIncrement;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String OTHER_STUDENT = "e8445639-917a-4396-8aaa-4a68dd11e4c7";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    private static final String FILE_PATH = "src/main/resources/static/sample-data/test.pdf";

    private final HttpServletRequest request;

    @Autowired
    public ExamCommonServiceTest(ExamTeacherService examTeacherService, ExamCommonService examCommonService, ExamTeacherService examTeacherService1, ExamStudentService examStudentService, TestRepositoryUtil testRepositoryUtil, HttpServletRequest request) {
        this.examCommonService = examCommonService;
        this.examTeacherService = examTeacherService1;
        this.examStudentService = examStudentService;
        this.testRepositoryUtil = testRepositoryUtil;
        this.request = request;
    }

    @BeforeEach
    public void getAutoIncrement() {
        assessmentAutoIncrement = testRepositoryUtil.getAutoIncrement("assessment");
        // submissionAutoIncrement = testRepositoryUtil.getAutoIncrement("submission");
    }


    @Test
    public void testGetExamList() {

        createTextDummy(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1));
        createTextDummy(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1));

        Long id1 = assessmentAutoIncrement;
        Long id2 = assessmentAutoIncrement + 1;
        int page = 1;
        String classroomId = CLASSROOM_ID;

        ExamPageResultDto<ExamListResponseDto> dto = examCommonService.getExamList(1, classroomId);
        Assertions.assertThat(dto.getDtoList().size()).isEqualTo(2);
        Assertions.assertThat(dto.getPage()).isEqualTo(page);
        Assertions.assertThat(dto.getDtoList().getFirst().getClassroomId()).isEqualTo(classroomId);
        Assertions.assertThat(dto.getDtoList().getFirst().getId()).isEqualTo(id1);
        Assertions.assertThat(dto.getDtoList().getFirst().getStatus()).isEqualTo(AssessmentStatus.IN_PROGRESS);
        Assertions.assertThat(dto.getDtoList().getLast().getStatus()).isEqualTo(AssessmentStatus.SCHEDULED);
    }




    private ExamDto createTextDummy() {

        String content = "1번 문제. 세상에서 가장 긴 것은?";
        String teacherId = TEACHER_ID;
        String classroomId = CLASSROOM_ID;
        String title = "test";
        String description = "test";
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        List<String> studentList = new ArrayList<>();
        studentList.add(STUDENT_ID);
        studentList.add(OTHER_STUDENT);

        return examTeacherService.create(content, teacherId, classroomId, title, description, startTime,
                endTime, studentList);
    }

    private ExamDto createTextDummy(LocalDateTime startTime, LocalDateTime endTime) {

        String content = "1번 문제. 세상에서 가장 긴 것은?";
        String teacherId = TEACHER_ID;
        String classroomId = CLASSROOM_ID;
        String title = "test";
        String description = "test";
        List<String> studentList = new ArrayList<>();
        studentList.add(STUDENT_ID);
        studentList.add(OTHER_STUDENT);

        return examTeacherService.create(content, teacherId, classroomId, title, description, startTime,
                endTime, studentList);
    }

    private ExamDto createFileDummy() throws FileNotFoundException {

        String filePath = FILE_PATH;
        InputStream inputStream = new FileInputStream(filePath);
        String teacherId = TEACHER_ID;
        String classroomId = CLASSROOM_ID;
        String title = "test";
        String description = "test";
        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        List<String> studentList = new ArrayList<>();
        studentList.add(STUDENT_ID);
        studentList.add(OTHER_STUDENT);

        return examTeacherService.create(inputStream, "originalFileName", teacherId,
                classroomId, title, description, startTime,
                endTime, studentList);
    }

    private void setUserId(String userId) {

        JwtPayloadDto dto = JwtPayloadDto.builder()
                .sub(userId)
                .build();

        request.setAttribute("jwtPayload", dto);
    }
}
