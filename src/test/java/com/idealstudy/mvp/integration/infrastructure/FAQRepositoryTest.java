package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQPageResultDto;
import com.idealstudy.mvp.application.repository.preclass.FAQRepository;
import com.idealstudy.mvp.mapstruct.FAQMapper;
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
@Transactional
@Slf4j
public class FAQRepositoryTest {

    private final FAQRepository faqRepository;

    // 테스트 이외에 의존성 없음
    private final TestRepositoryUtil testRepositoryUtil;
    private Long autoIncrement;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String TABLE_NAME = "faq";

    @Autowired
    public FAQRepositoryTest(FAQRepository faqRepository,TestRepositoryUtil testRepositoryUtil) {
        this.faqRepository = faqRepository;
        this.testRepositoryUtil = testRepositoryUtil;
    }

    @BeforeEach
    public void getAutoIncrement() {
        autoIncrement = testRepositoryUtil.getAutoIncrement(TABLE_NAME);
    }

    @Test
    public void createAndFindOne() {

        String createdBy = TEACHER_ID;
        FAQDto createDto = createDummy(createdBy);

        FAQDto findDto = faqRepository.findById(autoIncrement);
        Assertions.assertThat(findDto.getId()).isEqualTo(autoIncrement);
        Assertions.assertThat(findDto.getCreatedBy()).isEqualTo(createDto.getCreatedBy());
        Assertions.assertThat(findDto.getClassroomId()).isEqualTo(createDto.getClassroomId());
        Assertions.assertThat(findDto.getTitle()).isEqualTo(createDto.getTitle());
        Assertions.assertThat(findDto.getContent()).isEqualTo(createDto.getContent());
    }

    /*
    @Test
    public void findList() {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(1)
                .size(10)
                .build();

        FAQPageResultDto resultDto = faqRepository.findList(requestDto, CLASSROOM_ID);
        List<FAQDto> list = resultDto.getDtoList();
        FAQDto dto = list.getFirst();

        Assertions.assertThat(resultDto.getPage()).isEqualTo(1);
        Assertions.assertThat(dto.getId()).isEqualTo(ID);
        Assertions.assertThat(dto.getCreatedBy()).isEqualTo(TEACHER_ID);
        Assertions.assertThat(dto.getClassroomId()).isEqualTo(CLASSROOM_ID);
    }

    @Test
    public void update() {

        String newTitle = "동영상 시청 팁";

        Long id = ID;


        FAQDto dto = FAQDto.builder()
                .id(ID)
                .title(newTitle)
                .build();

        FAQDto updateDto = faqRepository.update(dto);

        Assertions.assertThat(updateDto.getId()).isEqualTo(ID);
        Assertions.assertThat(updateDto.getCreatedBy()).isEqualTo(TEACHER_ID);
        Assertions.assertThat(updateDto.getTitle()).isEqualTo(newTitle);
    }

    @Test
    public void delete() {

        faqRepository.delete(ID);

        Assertions.assertThatThrownBy(() -> {faqRepository.findById(ID);})
                .isInstanceOf(NoSuchElementException.class);
    }

     */

    private FAQDto createDummy(String createdBy) {

        String title = "과제 하는 방법";
        String content = "<p>과제를 작성하고 파일 형태로 첨부하여 제출할 것.</p>";
        String classroomId = CLASSROOM_ID;

        return faqRepository.create(title, content, classroomId, createdBy);
    }
}
