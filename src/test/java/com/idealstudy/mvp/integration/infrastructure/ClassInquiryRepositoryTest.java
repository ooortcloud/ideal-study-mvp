package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryPageResultDto;
import com.idealstudy.mvp.enums.classroom.Visibility;
import com.idealstudy.mvp.application.repository.preclass.ClassInquiryRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
@Slf4j
@Transactional
public class ClassInquiryRepositoryTest {

    @Autowired
    private final ClassInquiryRepository classInquiryRepository;

    // 테스트 이외에 의존성 없음
    @Autowired
    private final TestRepositoryUtil testRepositoryUtil;
    private Long autoIncrement;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    private static final Long EXIST_ID = 1L;

    private static final String TABLE_NAME = "class_inquiry";

    @Autowired
    public ClassInquiryRepositoryTest(ClassInquiryRepository classInquiryRepository, TestRepositoryUtil testRepositoryUtil) {
        this.classInquiryRepository = classInquiryRepository;
        this.testRepositoryUtil = testRepositoryUtil;
    }

    @BeforeEach
    public void getAutoIncrement() {
        autoIncrement = testRepositoryUtil.getAutoIncrement(TABLE_NAME);
    }

    @Test
    public void createAndFindById() {

        String title = "문의드립니다.";
        String content = "<p>사실 없습니다.</p>";
        Visibility visibility = Visibility.PUBLIC;

        classInquiryRepository.create(title, content, CLASSROOM_ID,  visibility);

        ClassInquiryDto dto = classInquiryRepository.findById(autoIncrement);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getContent()).isEqualTo(content);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(CLASSROOM_ID);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(STUDENT_ID);
    }

    @Test
    public void update() {

        String title = "문의드립니다.";
        String content = "<p>사실 없습니다.</p>";
        Visibility visibility = Visibility.PUBLIC;

        ClassInquiryDto dto = classInquiryRepository.update(EXIST_ID, title, content, CLASSROOM_ID, visibility);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getContent()).isEqualTo(content);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(CLASSROOM_ID);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(STUDENT_ID);
    }

    @Test
    public void delete() {

        boolean result = classInquiryRepository.delete(EXIST_ID);

        Assertions.assertThatThrownBy(() -> classInquiryRepository.findById(EXIST_ID))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void findOne() {

        ClassInquiryDto dto = classInquiryRepository.findById(EXIST_ID);


        log.info("dto 정보: " + dto);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(CLASSROOM_ID);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(STUDENT_ID);
    }

    @Test
    public void findByClassId() {

        int page = 1;
        String classId = CLASSROOM_ID;
        String writer = STUDENT_ID;

        ClassInquiryPageResultDto dto = classInquiryRepository.findListByClassId(classId, page);

        List<ClassInquiryDto> list = dto.getDtoList();
        ClassInquiryDto firstDto = list.getFirst();

        log.info("dto 정보: " + firstDto);
        Assertions.assertThat(list.size()).isEqualTo(1);
        Assertions.assertThat(firstDto.getClassroomId()).isEqualTo(classId);
        Assertions.assertThat(firstDto.getCreatedBy()).isEqualTo(writer);
    }

    @Test
    public void findByMemberId() {

        int page = 1;
        String classId = CLASSROOM_ID;
        String writer = STUDENT_ID;

        ClassInquiryPageResultDto dto = classInquiryRepository.findListByMemberId(writer, page);

        List<ClassInquiryDto> list = dto.getDtoList();
        ClassInquiryDto firstDto = list.getFirst();

        log.info("dto 정보: " + firstDto);
        Assertions.assertThat(list.size()).isEqualTo(1);
        Assertions.assertThat(firstDto.getClassroomId()).isEqualTo(classId);
        Assertions.assertThat(firstDto.getCreatedBy()).isEqualTo(writer);
    }
}
