package com.idealstudy.mvp.unit;

import com.idealstudy.mvp.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQPageResultDto;
import com.idealstudy.mvp.application.service.classroom.preclass.FAQService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
@Transactional
public class FAQServiceTest {

    private final FAQService faqService;

    // 테스트 이외에 의존성 없음
    private final TestRepositoryUtil testRepositoryUtil;
    private Long autoIncrement;

    private static final Long FAQ_ID = 1L;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String TABLE_NAME = "faq";

    @Autowired
    public FAQServiceTest(FAQService faqService, TestRepositoryUtil testRepositoryUtil) {
        this.faqService = faqService;
        this.testRepositoryUtil = testRepositoryUtil;
    }

    @BeforeEach
    public void getAutoIncrement() {
        autoIncrement = testRepositoryUtil.getAutoIncrement(TABLE_NAME);
    }

    @Test
    public void createAndFindById() {

        String title = "과제 하는 방법";
        String content = "<p>과제를 작성하고 파일 형태로 첨부하여 제출할 것.</p>";

        faqService.create(TEACHER_ID, CLASSROOM_ID, title, content);

        FAQDto dto = faqService.findById(autoIncrement);
        Assertions.assertThat(dto.getId()).isEqualTo(autoIncrement);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(TEACHER_ID);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(CLASSROOM_ID);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getContent()).isEqualTo(content);
    }

    @Test
    public void findList() {

        int page = 1;

        FAQPageResultDto resultDto = faqService.findList(page, CLASSROOM_ID);
        List<FAQDto> list = resultDto.getDtoList();
        FAQDto dto = list.getFirst();

        Assertions.assertThat(resultDto.getPage()).isEqualTo(page);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(TEACHER_ID);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(CLASSROOM_ID);
    }

    @Test
    public void update() {

        String newTitle = "동영상 시청 팁";
        String newContent = "<p>동영상 시청은 어렵지 않습니다.</p>";
        String teacherId = TEACHER_ID;

        FAQDto updateDto = faqService.update(FAQ_ID, newTitle, newContent, teacherId);

        Assertions.assertThat(updateDto.getCreatedBy()).isEqualTo(teacherId);
        Assertions.assertThat(updateDto.getTitle()).isEqualTo(newTitle);
        Assertions.assertThat(updateDto.getContent()).isEqualTo(newContent);
    }

    @Test
    public void delete() {

        FAQDto createDto = createDummy();

        faqService.delete(createDto.getId(), createDto.getCreatedBy());

        Assertions.assertThatThrownBy(() -> faqService.findById(createDto.getId()))
                .isInstanceOf(NoSuchElementException.class);
    }

    private FAQDto createDummy() {

        String teacherId = TEACHER_ID;
        String classroomId = CLASSROOM_ID;
        String title = "과제 하는 방법";
        String content = "<p>과제를 작성하고 파일 형태로 첨부하여 제출할 것.</p>";

        return faqService.create(teacherId, classroomId, title, content);
    }
}
