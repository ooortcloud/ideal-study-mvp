package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.application.dto.classroom.ClassroomPageResultDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.repository.ClassroomRepository;
import com.idealstudy.mvp.enums.classroom.ClassroomStatus;
import com.idealstudy.mvp.integration.infrastructure.helper.InfraDummyClassGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class ClassroomRepositoryTest {

    private final ClassroomRepository classroomRepository;

    private final InfraDummyClassGenerator dummyClassGenerator;

    @Autowired
    public ClassroomRepositoryTest(ClassroomRepository classroomRepository, InfraDummyClassGenerator dummyClassGenerator) {
        this.classroomRepository = classroomRepository;
        this.dummyClassGenerator = dummyClassGenerator;
    }

    @Test
    public void createAndSelectOne() {

        Map<String, Object> dummyData = dummyClassGenerator.createDummy();
        ClassroomResponseDto dummyClass = (ClassroomResponseDto) dummyData.get("classroomResponseDto");

        ClassroomResponseDto findClass = classroomRepository.findById(dummyClass.getId());
        assertThat(findClass.getTitle()).isEqualTo(dummyClass.getTitle());
        assertThat(findClass.getDescription()).isEqualTo(dummyClass.getDescription());
        assertThat(findClass.getCapacity()).isEqualTo(dummyClass.getCapacity());
        assertThat(findClass.getThumbnail()).isEqualTo(dummyClass.getThumbnail());
        assertThat(findClass.getCreatedBy()).isEqualTo(dummyClass.getCreatedBy());
        assertThat(findClass.getStatus()).isEqualTo(ClassroomStatus.SETUP);
    }

    @Test
    public void createAndSelectList() {

        Map<String, Object> dummyData1 = dummyClassGenerator.createDummy();
        ClassroomResponseDto dummyClass1 = (ClassroomResponseDto) dummyData1.get("classroomResponseDto");
        Map<String, Object> dummyData2 = dummyClassGenerator.createDummy();
        ClassroomResponseDto dummyClass2 = (ClassroomResponseDto) dummyData2.get("classroomResponseDto");

        int page = 1;
        ClassroomStatus status = null;
        ClassroomPageResultDto dto =  classroomRepository.findList(page, status);
        assertThat(dto.getDtoList().size()).isGreaterThanOrEqualTo(2);

    }

    @Test
    public void createAndSelectList_Opened() {

        Map<String, Object> dummyData1 = dummyClassGenerator.createDummy();
        ClassroomResponseDto dummyClass1 = (ClassroomResponseDto) dummyData1.get("classroomResponseDto");
        Map<String, Object> dummyData2 = dummyClassGenerator.createDummy();
        ClassroomResponseDto dummyClass2 = (ClassroomResponseDto) dummyData2.get("classroomResponseDto");

        classroomRepository.updateClassroomStatus(dummyClass1.getId(), ClassroomStatus.OPEN);

        int page = 1;
        ClassroomStatus status = ClassroomStatus.OPEN;
        ClassroomPageResultDto dto = classroomRepository.findList(page, status);
        assertThat(dto.getDtoList().size()).isEqualTo(1);
    }

    @Test
    public void createAndSelectList_Closed() {

        Map<String, Object> dummyData1 = dummyClassGenerator.createDummy();
        ClassroomResponseDto dummyClass1 = (ClassroomResponseDto) dummyData1.get("classroomResponseDto");
        Map<String, Object> dummyData2 = dummyClassGenerator.createDummy();
        ClassroomResponseDto dummyClass2 = (ClassroomResponseDto) dummyData2.get("classroomResponseDto");

        int page = 1;
        ClassroomStatus status = ClassroomStatus.CLOSED;
        ClassroomPageResultDto dto = classroomRepository.findList(page, status);
        assertThat(dto.getDtoList().size()).isEqualTo(0);
    }

    @Test
    public void createAndUpdate() {

        Map<String, Object> dummyData = dummyClassGenerator.createDummy();
        ClassroomResponseDto dummyClass = (ClassroomResponseDto) dummyData.get("classroomResponseDto");

        String title = "update";
        String desc = "update";
        Integer capacity = 10;
        String thumbnail = null;

        ClassroomResponseDto updateDto = classroomRepository.update(dummyClass.getId(), title, desc, capacity, thumbnail);
        assertThat(updateDto.getTitle()).isEqualTo(title);
        assertThat(updateDto.getDescription()).isEqualTo(desc);
        assertThat(updateDto.getCapacity()).isEqualTo(capacity);
        assertThat(updateDto.getThumbnail()).isEqualTo(dummyClass.getThumbnail());
        assertThat(updateDto.getCreatedBy()).isEqualTo(dummyClass.getCreatedBy());
    }

    @Test
    public void createAndDelete() {

        Map<String, Object> dummyData = dummyClassGenerator.createDummy();
        ClassroomResponseDto dummyClass = (ClassroomResponseDto) dummyData.get("classroomResponseDto");

        classroomRepository.deleteById(dummyClass.getId());

        assertThatThrownBy(() -> classroomRepository.findById(dummyClass.getId()));
    }
}
