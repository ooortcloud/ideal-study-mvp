package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLecturePageResultDto;
import com.idealstudy.mvp.application.repository.inclass.RecordLectureRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@SpringBootTest
@Transactional
public class RecordLectureRepositoryTest {

    private final RecordLectureRepository recordLectureRepository;

    // 테스트 이외에 의존성 없음

    @Autowired
    private final TestRepositoryUtil testRepositoryUtil;
    private Long autoIncrement;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    private static final Long EXIST_ID = 1L;

    private static final String TABLE_NAME = "record_lecture";

    private static final File TEST_FILE = new File("src/main/resources/static/video/test.mp4");


    @Autowired
    public RecordLectureRepositoryTest(RecordLectureRepository recordLectureRepository,
                                       TestRepositoryUtil testRepositoryUtil) {
        this.recordLectureRepository = recordLectureRepository;
        this.testRepositoryUtil = testRepositoryUtil;
    }

    @BeforeEach
    public void getAutoIncrement() {
        autoIncrement = testRepositoryUtil.getAutoIncrement(TABLE_NAME);
    }

    @Test
    public void testVimeoUpload(){

        String classroomId = CLASSROOM_ID;
        String title = "test";
        String description = "test";
        int order = 1;

        Assertions.assertThatCode(() ->
                recordLectureRepository.create(classroomId, title, description, TEST_FILE, order))
                .doesNotThrowAnyException();
    }

    @Test
    public void testVimeoEdit() {

        Long id = null;
        String videoEndPoint = "/videos/1037866255";
        String title = "update";
        String description = "update";

        Assertions.assertThatCode(() ->
                recordLectureRepository.updateVideo(id, title, description, videoEndPoint))
                .doesNotThrowAnyException();
    }

    @Test
    public void testVimeoDelete() {

        Long id = null;
        String videoEndPoint = "/videos/1037866255";

        Assertions.assertThatCode(() ->
                        recordLectureRepository.deleteVideo(id, videoEndPoint))
                .doesNotThrowAnyException();
    }

    @Test
    public void testCreateOld() {

        String classroomId = CLASSROOM_ID;
        String title = "test";
        String description = "test";
        int order = 1;

        RecordLectureDto dto = recordLectureRepository.create(classroomId, title, description, TEST_FILE, order);

        Assertions.assertThat(dto.getClassroomId()).isEqualTo(classroomId);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getDescription()).isEqualTo(description);
        Assertions.assertThat(dto.getOrderNum()).isEqualTo(order);
    }

    @Test
    public void testCreate() {

        String classroomId = CLASSROOM_ID;
        String title = "test";
        String description = "test";
        int order = 1;
        String videoEndPoint = "/videos/1038052017";

        RecordLectureDto dto = recordLectureRepository.create(classroomId, title, description, videoEndPoint, order);

        Assertions.assertThat(dto.getClassroomId()).isEqualTo(classroomId);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getDescription()).isEqualTo(description);
        Assertions.assertThat(dto.getOrderNum()).isEqualTo(order);
    }

    @Test
    public void testGetDetail() {

        int order = 1;
        Long id = autoIncrement;
        String classroomId = CLASSROOM_ID;
        String title = "test";
        String description = "test";
        String videoEndPoint = "/videos/1038052017";

        createDummy(order);

        RecordLectureDto dto = recordLectureRepository.getDetail(id);

        Assertions.assertThat(dto.getClassroomId()).isEqualTo(classroomId);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getDescription()).isEqualTo(description);
        Assertions.assertThat(dto.getOrderNum()).isEqualTo(order);
        Assertions.assertThat(dto.getUrl()).isEqualTo(videoEndPoint);
    }

    /*
    @Test
    public void testGetList() {

        createDummy();
        createDummy();
        autoIncrement++;

        String classroomId = CLASSROOM_ID;

        RecordLecturePageResultDto pageResultDto = recordLectureRepository.selectList(classroomId);
        Assertions.assertThat(pageResultDto.getDtoList().size()).isEqualTo(2+1);
        Assertions.assertThat(pageResultDto.getDtoList().getFirst().getClassroomId()).isEqualTo(classroomId);
    }

     */

    @Test
    public void testUpdate() {

        createDummy();

        Long id = autoIncrement;
        String title = "update";
        String desc = "update";

        RecordLectureDto dto = recordLectureRepository.update(id, title, desc, null, null);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getDescription()).isEqualTo(desc);
    }

    @Test
    public void testDelete() {

        createDummy();

        Long id = autoIncrement;

        recordLectureRepository.delete(id);

        Assertions.assertThatThrownBy(() -> recordLectureRepository.getDetail(id));
    }

    @Deprecated
    private RecordLectureDto createDummy(int order) {

        String classroomId = CLASSROOM_ID;
        String title = "test";
        String description = "test";
        String videoEndPoint = "/videos/1038052017";

        return recordLectureRepository.create(classroomId, title, description, videoEndPoint, order);
    }

    private RecordLectureDto createDummy() {

        String classroomId = CLASSROOM_ID;
        String title = "test";
        String description = "test";
        String videoEndPoint = "/videos/1038052017";

        return recordLectureRepository.create(classroomId, title, description, videoEndPoint, null);
    }
}

