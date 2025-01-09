package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLecturePageResultDto;
import com.idealstudy.mvp.enums.classroom.Platform;
import com.idealstudy.mvp.application.repository.inclass.LiveLectureRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class LiveLectureRepositoryTest {

    private final LiveLectureRepository liveLectureRepository;

    private final TestRepositoryUtil testRepositoryUtil;
    private Long autoIncrement;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    private static final String TABLE_NAME = "live_lecture";

    @Autowired
    public LiveLectureRepositoryTest(LiveLectureRepository liveLectureRepository, TestRepositoryUtil testRepositoryUtil) {
        this.liveLectureRepository = liveLectureRepository;
        this.testRepositoryUtil = testRepositoryUtil;
    }

    @BeforeEach
    public void getAutoIncrement() {
        autoIncrement = testRepositoryUtil.getAutoIncrement(TABLE_NAME);
    }

    @Test
    public void testCreate() {

        String classroomId = CLASSROOM_ID;
        String title = "test";
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        String studySpace = "test";
        Platform flatform = Platform.ZOOM;

        LiveLectureDto dto = liveLectureRepository.create(classroomId, title, startTime, endTime, studySpace,
                flatform);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getStartTime()).isEqualTo(startTime);
        Assertions.assertThat(dto.getEndTime()).isEqualTo(endTime);
        Assertions.assertThat(dto.getStudySpace()).isEqualTo(studySpace);
        Assertions.assertThat(dto.getPlatform()).isEqualTo(flatform);
    }

    @Test
    public void testGetOne() {

        String title = "test";
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        String studySpace = "test";
        Platform flatform = Platform.ZOOM;

        createDummy();

        Long id = autoIncrement;

        LiveLectureDto dto = liveLectureRepository.getOne(id);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getStartTime()).isEqualTo(startTime);
        Assertions.assertThat(dto.getEndTime()).isEqualTo(endTime);
        Assertions.assertThat(dto.getStudySpace()).isEqualTo(studySpace);
        Assertions.assertThat(dto.getPlatform()).isEqualTo(flatform);
    }

    @Test
    public void testUpdate() {

        String title = "update";
        LocalDateTime endTime = LocalDateTime.now().plusHours(2);
        String studySpace = "update";
        Platform flatform = Platform.OFFLINE;

        createDummy();

        Long id = autoIncrement;

        LiveLectureDto dto = liveLectureRepository.update(id, title, null, endTime, studySpace, flatform);
        Assertions.assertThat(dto.getTitle()).isEqualTo(title);
        Assertions.assertThat(dto.getEndTime()).isEqualTo(endTime);
        Assertions.assertThat(dto.getStudySpace()).isEqualTo(studySpace);
        Assertions.assertThat(dto.getPlatform()).isEqualTo(flatform);
    }

    @Test
    public void testDelete() {

        Long id = autoIncrement;

        createDummy();

        liveLectureRepository.delete(id);

        Assertions.assertThatThrownBy(() -> liveLectureRepository.getOne(autoIncrement));
    }

    @Test
    public void testGetList() {

        LocalDateTime startTime = LocalDateTime.now();

        createDummy(startTime);
        createDummy(startTime.plusHours(1));

        Long firstId = autoIncrement;
        Long secondId = firstId + 1;

        String classroomId = CLASSROOM_ID;

        LiveLecturePageResultDto dto = liveLectureRepository.getList(classroomId);
        Assertions.assertThat(dto.getDtoList().size()).isEqualTo(2);
        Assertions.assertThat(dto.getDtoList().get(0).getId()).isEqualTo(firstId);
        Assertions.assertThat(dto.getDtoList().get(1).getId()).isEqualTo(secondId);
        
    }

    private void createDummy() {

        String classroomId = CLASSROOM_ID;
        String title = "test";
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        String studySpace = "test";
        Platform flatform = Platform.ZOOM;

        LiveLectureDto dto = liveLectureRepository.create(CLASSROOM_ID, title, startTime, endTime, studySpace,
                flatform);
    }

    private void createDummy(LocalDateTime startTime) {

        String classroomId = CLASSROOM_ID;
        String title = "test";
        LocalDateTime endTime = startTime.plusHours(1);
        String studySpace = "test";
        Platform flatform = Platform.ZOOM;

        LiveLectureDto dto = liveLectureRepository.create(CLASSROOM_ID, title, startTime, endTime, studySpace,
                flatform);
    }
}
