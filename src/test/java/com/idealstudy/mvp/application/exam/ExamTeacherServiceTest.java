package com.idealstudy.mvp.application.exam;

import com.idealstudy.mvp.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamFeedbackDetailResponse;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamFeedbackListResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.exam.ExamPageResultDto;
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
public class ExamTeacherServiceTest {

    private final ExamTeacherService examTeacherService;
    
    // 시험  제출 용
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
    public ExamTeacherServiceTest(ExamTeacherService examTeacherService, ExamStudentService examStudentService, TestRepositoryUtil testRepositoryUtil, HttpServletRequest request) {
        this.examTeacherService = examTeacherService;
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
    public void testCreateTextExam() {

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

        ExamDto dto = examTeacherService.create(content, teacherId, classroomId, title, description, startTime,
                endTime, studentList);
        Assertions.assertThat(dto.getExamText()).isEqualTo(content);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(classroomId);
    }

    @Test
    public void testCreateFileExam() throws FileNotFoundException {

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

        ExamDto dto = examTeacherService.create(inputStream, "originalFileName", teacherId,
                classroomId, title, description, startTime,
                endTime, studentList);
        Assertions.assertThat(dto.getExamText()).isEqualTo(null);
        Assertions.assertThat(dto.getExamUri()).contains("src/main/resources/static/upload-exam");
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(classroomId);
    }

    @Test
    public void testUpdateTextExam() {

        String classroomId = CLASSROOM_ID;
        String teacherId = TEACHER_ID;
        createTextDummy();

        Long id = assessmentAutoIncrement;

        String title = "update";
        String content = "update content";

        ExamDto dto = examTeacherService.updateTextExam(id, title, null, null, null
        , content,classroomId, teacherId );

        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getExamText()).isEqualTo(content);
    }

    @Test
    public void testUpdateFileExam() throws FileNotFoundException {

        String classroomId = CLASSROOM_ID;
        String teacherId = TEACHER_ID;
        ExamDto oldDto = createFileDummy();

        Long id = assessmentAutoIncrement;
        String title = "update";
        File file = new File(FILE_PATH);
        String fileName = file.getName();
        InputStream is = new FileInputStream(file);

        ExamDto dto = examTeacherService.updateFileExam(id, title, null, null, null,
                teacherId, classroomId, is, fileName);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getExamUri()).isNotEqualTo(oldDto.getExamUri());
    }

    @Test
    public void testDeleteText() {

        createTextDummy();

        Long id = assessmentAutoIncrement;
        String teacherId = TEACHER_ID;
        String classroomId = CLASSROOM_ID;

        examTeacherService.deleteTextExam(id, teacherId, classroomId);
    }

    @Test
    public void testDeleteFile() throws FileNotFoundException {

        ExamDto dto = createFileDummy();

        Long id = assessmentAutoIncrement;
        String teacherId = TEACHER_ID;
        String classroomId = CLASSROOM_ID;

        examTeacherService.deleteFileExam(id, teacherId, classroomId);

        boolean exists = new File(dto.getExamUri()).exists();
        Assertions.assertThat(exists).isFalse();
    }

    @Test
    public void testGetFeedbackList() {

        String teacherId = TEACHER_ID;
        String classroomId = CLASSROOM_ID;

        createTextDummy();
        createTextDummy();
        Long id1 = assessmentAutoIncrement;
        Long id2 = id1 + 1;
        String title = "test";

        examTeacherService.feedbackToAll(id1, "feedback", teacherId, classroomId);
        examTeacherService.feedbackToAll(id2, "feedback", teacherId, classroomId);

        int page = 1;

        ExamPageResultDto<ExamFeedbackListResponseDto> pageResultDto =
                examTeacherService.getFeedbackList(teacherId, classroomId, page);

        Assertions.assertThat(pageResultDto.getPage()).isEqualTo(page);
        Assertions.assertThat(pageResultDto.getDtoList().size()).isEqualTo(2);
        Assertions.assertThat(pageResultDto.getDtoList().getFirst().getId()).isEqualTo(id2);
        Assertions.assertThat(pageResultDto.getDtoList().getFirst().getTitle()).isEqualTo(title);

    }

    @Test
    public void testGrade() {

        String studentId = STUDENT_ID;
        String teacherId = TEACHER_ID;

        ExamDto dto = createTextDummy();
        Long assessmentId = dto.getId();
        String classroomId = dto.getClassroomId();

        int score = 90;
        examTeacherService.grade(assessmentId, studentId, score, teacherId, classroomId);
    }

    @Test
    public void testPersonalFeedback() {

        String studentId = STUDENT_ID;
        String teacherId = TEACHER_ID;

        ExamDto dto = createTextDummy();
        Long assessmentId = dto.getId();
        String classroomId = dto.getClassroomId();

        String feedback = "참 잘했어요.";
        examTeacherService.personalFeedback(assessmentId, studentId, feedback, teacherId, classroomId);
    }

    @Test
    public void testDeletePersonalFeedback() {

        String studentId = STUDENT_ID;
        String teacherId = TEACHER_ID;

        setUserId(teacherId);

        ExamDto dto = createTextDummy();
        Long assessmentId = dto.getId();
        String classroomId = dto.getClassroomId();

        String feedback = "참 잘했어요.";
        examTeacherService.personalFeedback(assessmentId, studentId, feedback, teacherId, classroomId);

        examTeacherService.deletePersonalFeedback(assessmentId, studentId, teacherId, classroomId);
    }

    @Test
    public void testGetDetailFeedbackToAllForTeacher() {

        String teacherId = TEACHER_ID;
        String classroomId = CLASSROOM_ID;

        createTextDummy();
        Long id = assessmentAutoIncrement;
        String title = "test";

        String feedback = "열심히 해봅시다 여러분.";
        examTeacherService.feedbackToAll(id, feedback, teacherId, classroomId);

        ExamFeedbackDetailResponse dto = examTeacherService.getFeedbackToAllDetail(id, teacherId, classroomId);
        Assertions.assertThat(dto.getFeedbackToAll()).isEqualTo(feedback);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
    }

    @Test
    public void testGetSubmissionDetail() {

        String studentId = STUDENT_ID;
        String teacherId = TEACHER_ID;
        String classroomId = CLASSROOM_ID;

        setUserId(studentId);

        ExamDto dto = createTextDummy();
        Long assessmentId = dto.getId();

        SubmissionResponseDto responseDto = examTeacherService.getSubmissionDetail(assessmentId, studentId,
                teacherId, classroomId);
        Assertions.assertThat(responseDto.getAssessmentTitle()).isEqualTo("test");
        Assertions.assertThat(responseDto.getStudentName()).isEqualTo("김학생");
    }


    @Test
    public void testGetSubmissionList() {

        String studentId = STUDENT_ID;
        String classroomId = CLASSROOM_ID;
        String teacherId = TEACHER_ID;
        int page = 1;

        setUserId(studentId);

        ExamDto dto1 = createTextDummy();
        ExamDto dto2 = createTextDummy();

        Long id1 = dto1.getId();
        Long id2 = dto2.getId();

        String text = "답안 제출이요";

        examStudentService.submit(id1, studentId, text);
        examStudentService.submit(id2, studentId, text);

        SubmissionPageResultDto<SubmissionListResponseDto> dto = examTeacherService.getSubmissionList(teacherId, classroomId, page);
        Assertions.assertThat(dto.getDtoList().size()).isEqualTo(4);
        Assertions.assertThat(dto.getDtoList().getFirst().getStudentName()).contains("학생");
        Assertions.assertThat(dto.getDtoList().getFirst().getTitle()).isEqualTo("test");
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
