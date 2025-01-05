package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.integration.infrastructure.util.TestRepositoryUtil;
import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsPageResultDto;
import com.idealstudy.mvp.enums.classroom.MaterialsStatus;
import com.idealstudy.mvp.application.repository.inclass.MaterialsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@SpringBootTest
@Transactional
public class MaterialsRepositoryTest {

    private final MaterialsRepository materialsRepository;

    private final TestRepositoryUtil testRepositoryUtil;
    private Long autoIncrement;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String OTHER_STUDENT_ID = "e8445639-917a-4396-8aaa-4a68dd11e4c7";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    private static final String TABLE_NAME = "materials";

    @Autowired
    public MaterialsRepositoryTest(MaterialsRepository materialsRepository, TestRepositoryUtil testRepositoryUtil) {
        this.materialsRepository = materialsRepository;
        this.testRepositoryUtil = testRepositoryUtil;
    }

    @BeforeEach
    public void getAutoIncrement() {
        autoIncrement = testRepositoryUtil.getAutoIncrement(TABLE_NAME);
    }

    // @AfterEach
    public void deleteFile() {

        // 경로 찾는 거 구현해야 함.
        MaterialsDto dto = materialsRepository.getDetail(autoIncrement);

        File file = new File(dto.getMaterialUri());
        file.delete();
    }

    @Test
    public void testUpload() {

        String classroomId = CLASSROOM_ID;
        String studentId = STUDENT_ID;
        String title = "test";
        String description = "test";
        MaterialsStatus status = MaterialsStatus.INDIVIDUAL;
        // File file = new File("src/main/resources/static/upload-materials/test.pdf");
        String filePath = "src/main/resources/static/upload-materials/test.pdf";

        MaterialsDto dto = materialsRepository.upload(classroomId, studentId, null, status,title,
                description, filePath);

        org.junit.jupiter.api.Assertions.assertNotNull(dto);
        org.junit.jupiter.api.Assertions.assertEquals(description, dto.getDescription());
        org.junit.jupiter.api.Assertions.assertEquals(status, dto.getStatus());
        org.junit.jupiter.api.Assertions.assertEquals(studentId, dto.getStudentId());

        Assertions.assertThatCode(() -> new File(dto.getMaterialUri()))
                .doesNotThrowAnyException();
    }

    @Test
    public void teatGetDetail() {

        createDummyPublic();

        Long id = autoIncrement;

        MaterialsDto dto = materialsRepository.getDetail(id);
        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getDescription()).isEqualTo("test");
        Assertions.assertThat(dto.getStatus()).isEqualTo(MaterialsStatus.PUBLIC);

        Assertions.assertThatCode(() -> new File(dto.getMaterialUri()))
                .doesNotThrowAnyException();
    }


    /*
    @Test
    public void testUpdate() {

        createDummyIndividual(STUDENT_ID);

        Long id = autoIncrement;

        String description = "update";
        String materialUri = "src/main/resources/static/upload-materials/test02.txt";

        MaterialsDto dto = materialsRepository.update(id, null, null, null, description,
                materialUri);
        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getDescription()).isEqualTo(description);

        File uploadedFile = new File(dto.getMaterialUri());
        Assertions.assertThat(uploadedFile.getName()).contains("test02.txt");
    }

     */

    /**
     * AfterEach에서 오류 나면 성공한 것임.
     */
    @Test
    public void testDelete() {

        createDummyPublic();

        Long id = autoIncrement;

        materialsRepository.delete(id);

        Assertions.assertThatThrownBy(() -> materialsRepository.getDetail(id));
    }

    @Test
    public void testGetListByClassroom() {

        createDummyPublic();
        createDummyIndividual(STUDENT_ID);

        String classroomId = CLASSROOM_ID;
        int page = 1;

        MaterialsPageResultDto dto = materialsRepository.getListByClassroom(classroomId, page);
        List<MaterialsDto> list = dto.getDtoList();
        Assertions.assertThat(list.size()).isEqualTo(1);
        Assertions.assertThat(list.getFirst().getClassroomId()).isEqualTo(classroomId);
        Assertions.assertThat(list.getFirst().getStatus()).isEqualTo(MaterialsStatus.PUBLIC);
        Assertions.assertThat(dto.getPage()).isEqualTo(page);

        materialsRepository.delete(autoIncrement);
        materialsRepository.delete(autoIncrement+1);
    }

    @Test
    public void testGetListForTeacher() {

        createDummyPublic();
        createDummyIndividual(STUDENT_ID);

        String classroomId = CLASSROOM_ID;
        int page = 1;

        MaterialsPageResultDto dto = materialsRepository.getListForTeacher(classroomId, page);
        List<MaterialsDto> list = dto.getDtoList();
        Assertions.assertThat(list.size()).isEqualTo(2);
        Assertions.assertThat(list.getFirst().getClassroomId()).isEqualTo(classroomId);
        Assertions.assertThat(dto.getPage()).isEqualTo(page);

        materialsRepository.delete(autoIncrement);
        materialsRepository.delete(autoIncrement+1);
    }

    @Test
    public void testGetListForStudent() {

        createDummyPublic();
        createDummyIndividual(STUDENT_ID);
        createDummyIndividual(STUDENT_ID);
        createDummyIndividual(OTHER_STUDENT_ID);

        String classroomId = CLASSROOM_ID;
        String studentId = STUDENT_ID;
        int page = 1;

        MaterialsPageResultDto dto = materialsRepository.getListForStudent(classroomId, studentId, page);
        List<MaterialsDto> list = dto.getDtoList();
        Assertions.assertThat(list.size()).isEqualTo(2);
        Assertions.assertThat(list.getFirst().getClassroomId()).isEqualTo(classroomId);
        Assertions.assertThat(dto.getPage()).isEqualTo(page);

        materialsRepository.delete(autoIncrement);
        materialsRepository.delete(autoIncrement+1);
        materialsRepository.delete(autoIncrement+2);
        materialsRepository.delete(autoIncrement+3);
    }

    private void createDummyIndividual(String studentId) {

        String classroomId = CLASSROOM_ID;
        String title = "test";
        String description = "test";
        MaterialsStatus status = MaterialsStatus.INDIVIDUAL;
        // File file = new File("src/main/resources/static/upload-materials/test.pdf");
        String filePath = "src/main/resources/static/upload-materials/test.pdf";

        materialsRepository.upload(classroomId, studentId, null, status,title,
                description, filePath);
    }

    private void createDummyPublic() {

        String classroomId = CLASSROOM_ID;
        String title = "test";
        String description = "test";
        MaterialsStatus status = MaterialsStatus.PUBLIC;
        // File file = new File("src/main/resources/static/upload-materials/test.pdf");
        String filePath = "src/main/resources/static/upload-materials/test.pdf";

        materialsRepository.upload(classroomId, null, null, status,title,
                description, filePath);
    }
}
