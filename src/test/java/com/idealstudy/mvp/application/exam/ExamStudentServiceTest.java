package com.idealstudy.mvp.application.exam;

import com.idealstudy.mvp.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamFeedbackDetailResponse;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionFeedbackListResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionListResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionPageResultDto;
import com.idealstudy.mvp.application.service.classroom.inclass.exam.ExamStudentService;
import com.idealstudy.mvp.application.service.classroom.inclass.exam.ExamTeacherService;
import com.idealstudy.mvp.presentation.dto.classroom.SubmissionResponseDto;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import jakarta.servlet.http.HttpServletRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class ExamStudentServiceTest {

    private final ExamStudentService examStudentService;

    private final ExamTeacherService examTeacherService;

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

    // id 정보를 제공하기 위함
    private final HttpServletRequest request;

    @Autowired
    public ExamStudentServiceTest(ExamStudentService examStudentService, ExamTeacherService examTeacherService, TestRepositoryUtil testRepositoryUtil, HttpServletRequest request) {
        this.examStudentService = examStudentService;
        this.examTeacherService = examTeacherService;
        this.testRepositoryUtil = testRepositoryUtil;
        this.request = request;
    }

    @BeforeEach
    public void getAutoIncrement() {
        assessmentAutoIncrement = testRepositoryUtil.getAutoIncrement("assessment");
        submissionAutoIncrement = testRepositoryUtil.getAutoIncrement("submission");
    }

    @Test
    public void testTakeText() {

        createTextDummy();
        Long assessmentId = assessmentAutoIncrement;
        List<String> studentList = new ArrayList<>();
        studentList.add(STUDENT_ID);
        studentList.add(OTHER_STUDENT);
        String test = "1번 문제. 세상에서 가장 긴 것은?";

        setUserId(STUDENT_ID);

        String text = examStudentService.takeText(assessmentId, STUDENT_ID);
        Assertions.assertThat(text).isEqualTo(test);

        setUserId(OTHER_STUDENT);

        text = examStudentService.takeText(assessmentId, OTHER_STUDENT);
        Assertions.assertThat(text).isEqualTo(test);
    }

    @Test
    public void testTakeFile() throws FileNotFoundException {

        createFileDummy();
        Long assessmentId = assessmentAutoIncrement;
        List<String> studentList = new ArrayList<>();
        studentList.add(STUDENT_ID);
        studentList.add(OTHER_STUDENT);
        String fileName = "originalFileName";

        setUserId(STUDENT_ID);

        File file = examStudentService.takeFile(assessmentId, STUDENT_ID);
        Assertions.assertThat(file.getName()).contains(fileName);

        setUserId(OTHER_STUDENT);

        file = examStudentService.takeFile(assessmentId, OTHER_STUDENT);
        Assertions.assertThat(file.getName()).contains(fileName);

    }

    @Test
    public void testSubmitText() {

        String studentId = STUDENT_ID;
        setUserId(studentId);

        createTextDummy();
        Long assessmentId = assessmentAutoIncrement;

        String text = "제 생각에는 색연필이 가장 긴 것 같습니다.";

        examStudentService.submit(assessmentId, studentId, text);
    }

    @Test
    public void testSubmitFile() throws FileNotFoundException {

        String studentId = STUDENT_ID;
        setUserId(studentId);

        createTextDummy();
        Long assessmentId = assessmentAutoIncrement;

        File file = new File(FILE_PATH);
        InputStream is = new FileInputStream(file);
        String fileName = file.getName();

        examStudentService.submit(assessmentId, studentId, is, fileName);
    }

    @Test
    public void testModifySubmission() throws FileNotFoundException {

        String studentId = STUDENT_ID;
        setUserId(studentId);

        createTextDummy();
        Long assessmentId = assessmentAutoIncrement;

        File file = new File(FILE_PATH);
        InputStream is = new FileInputStream(file);
        String fileName = file.getName();

        examStudentService.submit(assessmentId, studentId, is, fileName);

        File newFile = new File("src/main/resources/static/sample-data/test.txt");
        is = new FileInputStream(newFile);
        fileName = newFile.getName();

        examStudentService.modifySubmission(assessmentId, studentId, is, fileName);
    }


    @Test
    public void testGetDetailFeedbackToAllForStudent() {

        String teacherId = TEACHER_ID;
        String classroomId = CLASSROOM_ID;
        String studentId = STUDENT_ID;

        createTextDummy();
        Long id = assessmentAutoIncrement;
        String title = "test";

        String feedback = "열심히 해봅시다 여러분.";
        examTeacherService.feedbackToAll(id, feedback, teacherId, classroomId);

        ExamFeedbackDetailResponse dto = examStudentService.getFeedbackToAllDetail(id, studentId, classroomId);
        Assertions.assertThat(dto.getFeedbackToAll()).isEqualTo(feedback);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
    }

    @Test
    public void testGetSubmissionDetail() {

        String studentId = STUDENT_ID;

        setUserId(studentId);

        ExamDto dto = createTextDummy();
        Long assessmentId = dto.getId();

        SubmissionResponseDto responseDto = examStudentService.getSubmissionDetail(assessmentId, studentId);
        Assertions.assertThat(responseDto.getAssessmentTitle()).isEqualTo("test");
        Assertions.assertThat(responseDto.getStudentName()).isEqualTo("김학생");
    }

    @Test
    public void testGetSubmissionList() {

        String studentId = STUDENT_ID;
        int page = 1;

        setUserId(studentId);

        createTextDummy();
        createTextDummy();

        Long id1 = assessmentAutoIncrement;
        Long id2 = id1 + 1;

        String text = "답안 제출이요";

        examStudentService.submit(id1, studentId, text);
        examStudentService.submit(id2, studentId, text);

        SubmissionPageResultDto<SubmissionListResponseDto> dto = examStudentService.getSubmissionList(studentId, page);
        Assertions.assertThat(dto.getDtoList().size()).isEqualTo(2);
        Assertions.assertThat(dto.getDtoList().getFirst().getStudentName()).isEqualTo("김학생");
        Assertions.assertThat(dto.getDtoList().getFirst().getTitle()).isEqualTo("test");
    }

    @Test
    public void testGetPersonalityFeedbackList() {

        String studentId = STUDENT_ID;

        setUserId(studentId);

        ExamDto examDto = createTextDummy();
        Long assessmentId = examDto.getId();
        String title = examDto.getTitle();
        String classroomName = examDto.getClassroomName();
        String classroomId = examDto.getClassroomId();

        String text = "빼빼로가 제일 길지 않을까요?";
        SubmissionDto submissionDto = examStudentService.submit(assessmentId, studentId, text);
        LocalDateTime startedAt = submissionDto.getStartedAt();

        String teacherId = TEACHER_ID;
        String feedback = "잘 했어요 김학생.";
        examTeacherService.personalFeedback(assessmentId, studentId, feedback, teacherId, classroomId);

        int page = 1;
        SubmissionPageResultDto<SubmissionFeedbackListResponseDto> dto = examStudentService.getPersonalityFeedbackList(studentId, page);
        Assertions.assertThat(dto.getDtoList().getFirst().getStudentId()).isEqualTo(studentId);
        Assertions.assertThat(dto.getDtoList().getFirst().getClassroomName()).isEqualTo(classroomName);
        Assertions.assertThat(dto.getDtoList().getFirst().getAssessmentTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getDtoList().getFirst().getStartedAt()).isEqualTo(startedAt);
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
