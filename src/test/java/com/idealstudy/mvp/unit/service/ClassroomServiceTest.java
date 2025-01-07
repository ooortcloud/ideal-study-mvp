package com.idealstudy.mvp.unit.service;

import com.idealstudy.mvp.application.component.ClassroomComponent;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.dto.member.TeacherDto;
import com.idealstudy.mvp.application.factory.FileManagerFactory;
import com.idealstudy.mvp.application.repository.ClassroomRepository;
import com.idealstudy.mvp.application.repository.LikedRepository;
import com.idealstudy.mvp.application.service.classroom.ClassroomService;
import com.idealstudy.mvp.application.service.domain_service.FileManager;
import com.idealstudy.mvp.enums.classroom.ClassroomStatus;
import com.idealstudy.mvp.unit.util.TestServiceUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class ClassroomServiceTest {

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private LikedRepository likedRepository;

    @Mock
    private ClassroomComponent classroomComponent;

    @Mock
    private FileManager fileManager;

    @Mock
    private FileManagerFactory fileManagerFactory;

    private ClassroomService classroomService;

    private static final String UPLOAD_PATH = "src/main/resources/static/temp/";

    @BeforeEach
    public void setup() {

        Mockito.when(fileManagerFactory.getFileManager(Mockito.any()))
                .thenReturn(fileManager);

        classroomService = new ClassroomService(classroomRepository, likedRepository, classroomComponent, fileManagerFactory, UPLOAD_PATH);
    }

    @Test
    public void createClassroom() throws IOException {

        // setup
        File tempUri = new File("src/main/resources/static/temp/apple.jpeg");
        InputStream is = new FileInputStream(tempUri);
        String originalFileName = tempUri.getName();

        String title = "temp";
        String description = "임시 클래스입니다.";
        Integer capacity = 20;

        TeacherDto teacherDto = TestServiceUtil.createDummyTeacherDto();
        String teacherId = teacherDto.getUserId();

        String savedUri = "src/main/resources/static/temp/abcdefghijk.jpeg";
        Mockito.when(fileManager.saveFile(is, originalFileName)).thenReturn(savedUri);

        ClassroomResponseDto dummyDto = ClassroomResponseDto.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .description(description)
                .capacity(capacity)
                .createdBy(teacherId)
                .thumbnail(savedUri)
                .status(ClassroomStatus.OPEN)
                .build();
        Mockito.when(classroomRepository.create(title, description, capacity, savedUri, teacherId))
                .thenReturn(dummyDto);

        // action
        ClassroomResponseDto responseDto = classroomService
                .createClassroom(title, description, capacity, teacherId, is, originalFileName);

        // validation
        Assertions.assertThat(responseDto).isNotNull();
        Assertions.assertThat(responseDto.getId()).isEqualTo(dummyDto.getId());
        Assertions.assertThat(responseDto.getTitle()).isEqualTo(dummyDto.getTitle());
        Assertions.assertThat(responseDto.getDescription()).isEqualTo(dummyDto.getDescription());
        Assertions.assertThat(responseDto.getCapacity()).isEqualTo(dummyDto.getCapacity());
        Assertions.assertThat(responseDto.getCreatedBy()).isEqualTo(dummyDto.getCreatedBy());
        Assertions.assertThat(responseDto.getThumbnail()).isEqualTo(dummyDto.getThumbnail());
        Assertions.assertThat(responseDto.getStatus()).isEqualTo(dummyDto.getStatus());
    }

    /// 로직이 지나치게 간단한 것들은 시간 절약을 위해 테스트 로직 작성 안함.
    public void getAllClassrooms() {

    }

    public void getClassroomById() {

    }

    public void deleteClassroom() {

    }

    @Test
    public void updateClassroom_ifInputStreamIsNotNull() throws IOException {

        // setup
        File tempUri = new File("src/main/resources/static/temp/apple.jpeg");
        InputStream is = new FileInputStream(tempUri);
        String originalFileName = tempUri.getName();

        String title = "temp";
        String description = "임시 클래스입니다.";
        Integer capacity = 20;

        TeacherDto teacherDto = TestServiceUtil.createDummyTeacherDto();
        String teacherId = teacherDto.getUserId();

        String savedUri = "src/main/resources/static/temp/abcdefghijk.jpeg";
        Mockito.when(fileManager.saveFile(is, originalFileName)).thenReturn(savedUri);

        String classroomId =UUID.randomUUID().toString();
        ClassroomResponseDto dummyDto = ClassroomResponseDto.builder()
                .id(classroomId)
                .title(title)
                .description(description)
                .capacity(capacity)
                .createdBy(teacherId)
                .thumbnail(savedUri)
                .status(ClassroomStatus.OPEN)
                .build();
        Mockito.when(classroomRepository.create(title, description, capacity, savedUri, teacherId))
                .thenReturn(dummyDto);

        ClassroomResponseDto responseDto = classroomService
                .createClassroom(title, description, capacity, teacherId, is, originalFileName);

        String newTitle = "update";
        String newDescription = "updated";
        Integer newCapacity = 15;
        String newSavedUri = "src/main/resources/static/temp/zxcvasdf.jpeg";
        Mockito.when(fileManager.saveFile(Mockito.any(InputStream.class), Mockito.anyString()))
                .thenReturn(newSavedUri);
        Mockito.doNothing().when(fileManager).deleteFile(Mockito.anyString());

        ClassroomResponseDto newDummyDto = ClassroomResponseDto.builder()
                .id(classroomId)
                .title(newTitle)
                .description(newDescription)
                .capacity(newCapacity)
                .createdBy(teacherId)
                .thumbnail(newSavedUri)
                .status(ClassroomStatus.OPEN)
                .build();
        Mockito.when(classroomRepository.update(classroomId, newTitle, newDescription, newCapacity, newSavedUri))
                .thenReturn(newDummyDto);

        Mockito.when(classroomRepository.findById(classroomId)).thenReturn(responseDto);

        // do
        ClassroomResponseDto resultDto =
                classroomService.updateClassroom(classroomId, newTitle, newDescription, newCapacity, teacherId, is);

        // action
        Assertions.assertThat(resultDto.getId()).isEqualTo(responseDto.getId());
        Assertions.assertThat(resultDto.getTitle()).isEqualTo(newTitle);
        Assertions.assertThat(resultDto.getDescription()).isEqualTo(newDescription);
        Assertions.assertThat(resultDto.getCapacity()).isEqualTo(newCapacity);
        Assertions.assertThat(resultDto.getCreatedBy()).isEqualTo(responseDto.getCreatedBy());
        Assertions.assertThat(resultDto.getThumbnail()).isEqualTo(newSavedUri);
    }

    @Test
    public void updateClassroom_ifInputStreamIsNull() throws IOException {

        // setup
        File tempUri = new File("src/main/resources/static/temp/apple.jpeg");
        InputStream is = new FileInputStream(tempUri);
        String originalFileName = tempUri.getName();

        String title = "temp";
        String description = "임시 클래스입니다.";
        Integer capacity = 20;

        TeacherDto teacherDto = TestServiceUtil.createDummyTeacherDto();
        String teacherId = teacherDto.getUserId();

        String savedUri = "src/main/resources/static/temp/abcdefghijk.jpeg";
        Mockito.when(fileManager.saveFile(is, originalFileName)).thenReturn(savedUri);

        String classroomId =UUID.randomUUID().toString();
        ClassroomResponseDto dummyDto = ClassroomResponseDto.builder()
                .id(classroomId)
                .title(title)
                .description(description)
                .capacity(capacity)
                .createdBy(teacherId)
                .thumbnail(savedUri)
                .status(ClassroomStatus.OPEN)
                .build();
        Mockito.when(classroomRepository.create(title, description, capacity, savedUri, teacherId))
                .thenReturn(dummyDto);

        ClassroomResponseDto responseDto = classroomService
                .createClassroom(title, description, capacity, teacherId, is, originalFileName);

        String newTitle = "update";
        String newDescription = "updated";
        Integer newCapacity = 15;

        ClassroomResponseDto newDummyDto = ClassroomResponseDto.builder()
                .id(classroomId)
                .title(newTitle)
                .description(newDescription)
                .capacity(newCapacity)
                .createdBy(teacherId)
                .thumbnail(savedUri)
                .status(ClassroomStatus.OPEN)
                .build();
        Mockito.when(classroomRepository.update(
                eq(classroomId), eq(newTitle), eq(newDescription), eq(newCapacity), any()))
                .thenReturn(newDummyDto);

        // do
        ClassroomResponseDto resultDto =
                classroomService.updateClassroom(classroomId, newTitle, newDescription, newCapacity, teacherId, null);

        // action
        Assertions.assertThat(resultDto.getId()).isEqualTo(responseDto.getId());
        Assertions.assertThat(resultDto.getTitle()).isEqualTo(newTitle);
        Assertions.assertThat(resultDto.getDescription()).isEqualTo(newDescription);
        Assertions.assertThat(resultDto.getCapacity()).isEqualTo(newCapacity);
        Assertions.assertThat(resultDto.getCreatedBy()).isEqualTo(responseDto.getCreatedBy());
        Assertions.assertThat(resultDto.getThumbnail()).isEqualTo(responseDto.getThumbnail());
    }

    // 실제 생성 로직을 거치는 것은 아니고, DTO 객체만 공급해줌.
    private ClassroomResponseDto createDummyClassroom(String teacherId) {

        String title = "temp";
        String description = "임시 클래스입니다.";
        Integer capacity = 20;
        String savedUri = "src/main/resources/static/temp/abcdefghijk.jpeg";

        ClassroomResponseDto dummyDto = ClassroomResponseDto.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .description(description)
                .capacity(capacity)
                .createdBy(teacherId)
                .thumbnail(savedUri)
                .status(ClassroomStatus.OPEN)
                .build();

        return dummyDto;
    }

    public void updateLiked() {

    }

    public void countLiked() {

    }
}
