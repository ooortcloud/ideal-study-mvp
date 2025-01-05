package com.idealstudy.mvp.unit;

import com.idealstudy.mvp.integration.infrastructure.util.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryPageResultDto;
import com.idealstudy.mvp.application.service.classroom.preclass.ClassInquiryService;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.enums.classroom.Visibility;
import com.idealstudy.mvp.enums.member.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.NoSuchElementException;

@SpringBootTest
@Transactional
public class ClassInquiryServiceTest {

    private final ClassInquiryService classInquiryService;

    // 테스트 이외에 의존성 없음
    @Autowired
    private final TestRepositoryUtil testRepositoryUtil;
    private Long autoIncrement;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String STUDENT_ID_NOT_WRITE = "e8445639-917a-4396-8aaa-4a68dd11e4c7";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    private static final Long EXIST_ID = 1L;

    private static final String TABLE_NAME = "class_inquiry";

    @Autowired
    public ClassInquiryServiceTest(ClassInquiryService classInquiryService, TestRepositoryUtil testRepositoryUtil) {
        this.classInquiryService = classInquiryService;
        this.testRepositoryUtil = testRepositoryUtil;
    }

    @BeforeEach
    public void getAutoIncrement() {
        autoIncrement = testRepositoryUtil.getAutoIncrement(TABLE_NAME);
    }

    @Test
    public void createAndFindOne() throws IOException {

        String title = "질문이 있습니다.";
        String content = "혹시 다음 수업 계획 있으신지요?";
        String classroomId = CLASSROOM_ID;
        String writer = STUDENT_ID;
        Visibility visibility = Visibility.PUBLIC;

        classInquiryService.create(title, content, classroomId, visibility);

        ClassInquiryDto dto = classInquiryService.findById(autoIncrement, writer);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(classroomId);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(writer);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getContent()).isEqualTo(content);
        Assertions.assertThat(dto.getVisibility()).isEqualTo(visibility);
    }

    @Test
    @DisplayName("자신이 쓴 비밀글 조회")
    public void findExist_Public() {

        String finder = STUDENT_ID;
        Long classInquiryId = EXIST_ID;
        String classroomId = CLASSROOM_ID;
        Visibility visibility = Visibility.PRIVATE;

        ClassInquiryDto dto = classInquiryService.findById(classInquiryId, finder);
        Assertions.assertThat(dto.getId()).isEqualTo(classInquiryId);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(classroomId);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(finder);
        Assertions.assertThat(dto.getVisibility()).isEqualTo(visibility);
    }

    @Test
    public void findListByClassId() throws IOException {

        String classId = CLASSROOM_ID;
        int page = 1;

        ClassInquiryPageResultDto resultDto = classInquiryService.findListByClassId(classId, page);
        ClassInquiryDto dto = resultDto.getDtoList().getFirst();

        Assertions.assertThat(resultDto.getPage()).isEqualTo(page);
        Assertions.assertThat(dto.getId()).isEqualTo(EXIST_ID);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(classId);
    }

    @Test
    public void findListByMemberId() throws IOException {

        String classId = CLASSROOM_ID;
        String writer = STUDENT_ID;
        int page = 1;

        ClassInquiryPageResultDto resultDto = classInquiryService.findListByMemberId(writer, page);
        ClassInquiryDto dto = resultDto.getDtoList().getFirst();

        Assertions.assertThat(resultDto.getPage()).isEqualTo(page);
        Assertions.assertThat(dto.getId()).isEqualTo(EXIST_ID);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(writer);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(classId);
    }

    @Test
    public void update() throws IOException {

        Long id = EXIST_ID;
        String title = "문의드립니다.";
        String content = "사실 없습니다.";
        String classId = CLASSROOM_ID;
        String userId = STUDENT_ID;
        Visibility visibility = Visibility.PUBLIC;

        testRepositoryUtil.setToken(userId, Role.ROLE_STUDENT);

        ClassInquiryDto dto = classInquiryService.update(id, title, content, classId, visibility, userId);
        Assertions.assertThat(dto.getId()).isEqualTo(id);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getContent()).isEqualTo(content);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(classId);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(userId);
        Assertions.assertThat(dto.getVisibility()).isEqualTo(visibility);
    }

    @Test
    public void delete() throws IOException {

        Long id = EXIST_ID;
        String writer = STUDENT_ID;

        classInquiryService.delete(id, writer);

        Assertions.assertThatThrownBy(() -> classInquiryService.findById(id, writer))
                .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    @DisplayName("타인이 쓴 비밀글 조회")
    public void findExist_Private() {

        String finder = STUDENT_ID_NOT_WRITE;
        Long faqId = EXIST_ID;

        Assertions.assertThatThrownBy(() -> classInquiryService.findById(faqId, finder))
                .isInstanceOf(SecurityException.class)
                .hasMessageContaining(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
    }

    @Test
    @DisplayName("본인이 아닌 사용자가 수정하려할 때 거부 테스트")
    public void update_notMine() {

        Long id = EXIST_ID;
        String title = "문의드립니다.";
        String content = "사실 없습니다.";
        String classId = CLASSROOM_ID;
        String userId = STUDENT_ID_NOT_WRITE;
        Visibility visibility = Visibility.PUBLIC;

        testRepositoryUtil.setToken(userId, Role.ROLE_STUDENT);

        Assertions.assertThatThrownBy(() -> classInquiryService.update(id, title, content, classId, visibility
                , userId))
                .isInstanceOf(SecurityException.class)
                .hasMessageContaining(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
    }

    @Test
    @DisplayName("본인이 아닌 사용자가 삭제하려할 때 거부 테스트")
    public void delete_notMine() {

        Long id = EXIST_ID;
        String writer = STUDENT_ID_NOT_WRITE;

        Assertions.assertThatThrownBy(() -> classInquiryService.delete(id, writer))
                .isInstanceOf(SecurityException.class)
                .hasMessageContaining(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
    }

}
