package com.idealstudy.mvp.infrastructure.impl.classroom.inclass;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsPageResultDto;
import com.idealstudy.mvp.enums.classroom.MaterialsStatus;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.MaterialsEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.StudentEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass.MaterialsJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.member.StudentJpaRepository;
import com.idealstudy.mvp.application.repository.inclass.MaterialsRepository;
import com.idealstudy.mvp.mapstruct.MaterialsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.UUID;
import java.util.function.Function;

@Repository
@Slf4j
public class MaterialsRepositoryImpl implements MaterialsRepository {

    private final MaterialsJpaRepository materialsJpaRepository;
    
    // 특정 classroom에 귀속되어야 하므로 사용
    private final ClassroomJpaRepository classroomJpaRepository;
    
    // INDIVIDUAL한 파일 제공인 경우 사용
    private final StudentJpaRepository studentJpaRepository;

    private final static int SIZE = 10;

    private final String UPLOAD_PATH;

    @Autowired
    public MaterialsRepositoryImpl(MaterialsJpaRepository materialsJpaRepository,
                                   ClassroomJpaRepository classroomJpaRepository,
                                   StudentJpaRepository studentJpaRepository,
                                   @Value("${upload.path}") String uploadPath) {
        this.materialsJpaRepository = materialsJpaRepository;
        this.classroomJpaRepository = classroomJpaRepository;
        this.studentJpaRepository = studentJpaRepository;

        UPLOAD_PATH = uploadPath;
    }

    @Override
    public MaterialsDto upload(String classroomId, String studentId, Integer orderNum, MaterialsStatus status,
                               String title, String description, String materialUri) {

        ClassroomEntity classroom = classroomJpaRepository.findById(classroomId).orElseThrow();

        StudentEntity student = null;
        if(studentId != null && status == MaterialsStatus.INDIVIDUAL)
            student = studentJpaRepository.findById(studentId).orElseThrow();

        if(studentId != null && status == MaterialsStatus.PUBLIC)
            throw new RuntimeException("학생 개인에게 전달하는 과제는 반드시 INDIVIDUAL이어야 합니다.");

        // String materialUri = uploadFile(file);

        MaterialsEntity entity = MaterialsEntity.builder()
                .classroom(classroom)
                .student(student)
                .orderNum(orderNum)
                .status(status)
                .title(title)
                .description(description)
                .materialUri(materialUri)
                .build();

        return MaterialsMapper.INSTANCE.toDto(materialsJpaRepository.save(entity));
    }

    @Override
    public MaterialsDto getDetail(Long id) {

        MaterialsEntity entity = materialsJpaRepository.findById(id).orElseThrow();
        return MaterialsMapper.INSTANCE.toDto(entity);
    }

    @Override
    public MaterialsPageResultDto getListByClassroom(String classroomId, int page) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)
                .size(SIZE)
                .build();

        Page<MaterialsEntity> resultPage = materialsJpaRepository.findByClassroom_classroomIdAndStatus(classroomId,
                MaterialsStatus.PUBLIC,
                requestDto.getPageable(Sort.by("regDate").descending()));

        Function<MaterialsEntity, MaterialsDto> fn = MaterialsMapper.INSTANCE::toDto;

        PageResultDto<MaterialsDto, MaterialsEntity> pageResultDto =
                new PageResultDto<>(resultPage, fn);

        return MaterialsMapper.INSTANCE.toPageResultDto(pageResultDto);
    }

    @Override
    public MaterialsPageResultDto getListForStudent(String classroomId, String studentId, int page) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)
                .size(SIZE)
                .build();

        Page<MaterialsEntity> resultPage = materialsJpaRepository
                .findByClassroom_classroomIdAndStudent_userIdOrStudent_userIdIsNull(
                classroomId, studentId,
                requestDto.getPageable(Sort.by("regDate").descending()));

        Function<MaterialsEntity, MaterialsDto> fn = MaterialsMapper.INSTANCE::toDto;

        PageResultDto<MaterialsDto, MaterialsEntity> pageResultDto =
                new PageResultDto<>(resultPage, fn);

        return MaterialsMapper.INSTANCE.toPageResultDto(pageResultDto);
    }

    @Override
    public MaterialsPageResultDto getListForTeacher(String classroomId, int page) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)
                .size(SIZE)
                .build();

        Page<MaterialsEntity> resultPage = materialsJpaRepository.findByClassroom_classroomId(classroomId,
                requestDto.getPageable(Sort.by("regDate").descending()));

        Function<MaterialsEntity, MaterialsDto> fn = MaterialsMapper.INSTANCE::toDto;

        PageResultDto<MaterialsDto, MaterialsEntity> pageResultDto =
                new PageResultDto<>(resultPage, fn);

        return MaterialsMapper.INSTANCE.toPageResultDto(pageResultDto);
    }

    @Override
    public MaterialsDto update(Long id, String studentId, Integer orderNum, MaterialsStatus status,
                               String description, String materialUri, String title) {

        MaterialsEntity entity = materialsJpaRepository.findById(id).orElseThrow();

        if (studentId != null && status == MaterialsStatus.INDIVIDUAL) {
            StudentEntity student = studentJpaRepository.findById(studentId).orElseThrow();
            entity.setStudent(student);
        }
        if(studentId != null && status == MaterialsStatus.PUBLIC)
            throw new RuntimeException("학생 개인에게 전달하는 과제는 반드시 INDIVIDUAL이어야 합니다.");
        if(studentId == null && status == MaterialsStatus.PUBLIC) {
            entity.setStudent(null);
            entity.setStatus(status);
        }

        if (orderNum != null) {
            entity.setOrderNum(orderNum);
        }

        if (status != null) {
            entity.setStatus(status);
        }

        if (description != null) {
            entity.setDescription(description);
        }

        if (materialUri != null) {
            // deleteFile(entity.getMaterialUri());
            // String materialUri = uploadFile(file);

            entity.setMaterialUri(materialUri);
        }

        if(title != null) {
            entity.setTitle(title);
        }

        return MaterialsMapper.INSTANCE.toDto(materialsJpaRepository.save(entity));
    }

    @Override
    public void delete(Long id) {

        MaterialsEntity entity = materialsJpaRepository.findById(id).orElseThrow();
        // deleteFile(entity.getMaterialUri());
        materialsJpaRepository.delete(entity);
    }

    @Deprecated
    private String uploadFile(File file) {

        String uuid = UUID.randomUUID().toString();
        String filePath = UPLOAD_PATH + uuid + "_" + file.getName();

        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = new FileOutputStream(filePath)) {

            byte[] buffer = new byte[8192]; // 8KB 버퍼 크기 설정

            int bytesRead;
            // EOF인 경우 파일 쓰기 종료. 그 외에는 buffer에 파일을 write.
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                // buffer에 저장된 파일 조각을 쓰는 작업
                outputStream.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.");
        }

        return filePath;
    }

    @Deprecated
    private void deleteFile(String materialUri) {

        File file = new File(materialUri);
        file.delete();
    }
}
