package com.idealstudy.mvp.application.service.classroom.inclass;

import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsPageResultDto;
import com.idealstudy.mvp.application.service.domain_service.ValidationManager;
import com.idealstudy.mvp.application.service.domain_service.FileManager;
import com.idealstudy.mvp.enums.classroom.MaterialsStatus;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.application.repository.inclass.MaterialsRepository;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@Slf4j
@Transactional
public class MaterialsService {

    private final MaterialsRepository materialsRepository;

    private final FileManager fileManager;

    private final ValidationManager validationService;

    @Autowired
    public MaterialsService(MaterialsRepository materialsRepository, ValidationManager validationService,
                            @Value("${upload.materials-path}") String uploadPath) {
        this.materialsRepository = materialsRepository;
        this.validationService = validationService;

        fileManager = new FileManager(uploadPath);
    }

    public MaterialsDto uploadPublic(String teacherId, String classroomId, String description, MultipartFile file,
                                     Integer orderNum, String title) {

        String studentId = null;
        MaterialsStatus status = MaterialsStatus.PUBLIC;

        return TryCatchServiceTemplate.execute(() -> tryCatchUpload(classroomId, studentId, orderNum,
                status, description, file, title),
                () -> validationService.validateTeacher(classroomId, teacherId), DBErrorMsg.CREATE_ERROR);
    }

    public MaterialsDto uploadIndividual(String teacherId, String classroomId, String studentId, String description,
                                         MultipartFile file, Integer orderNum, String title) {

        MaterialsStatus status = MaterialsStatus.INDIVIDUAL;

        return TryCatchServiceTemplate.execute(() -> tryCatchUpload(classroomId, studentId, orderNum, status,
                        description, file, title),
                () -> validationService.validateTeacher(classroomId, teacherId), DBErrorMsg.CREATE_ERROR);
    }

    public MaterialsDto getDetailForTeacher(Long id, String teacherId){

        return TryCatchServiceTemplate.execute(() -> {
            MaterialsDto dto = materialsRepository.getDetail(id);
            String classroomId = dto.getClassroomId();

            validationService.validateTeacher(classroomId, teacherId);

            return dto;
        },
        null, DBErrorMsg.SELECT_ERROR);
    }

    public MaterialsDto getDetailForStudent(Long id, String studentId){


        // 조회하는 유저가 해당 자료가 등록된 클래스에 속한 유저인지 확인해야 한다.
        // << PUBLIC인 경우 해당 사용자가 클래스에 속해있는 지만 비교ㅕ하면 된다.
        // 만일 INDIVIDUAL한 데이터인 경우, 해당 학생인지 정확히 판단해야 한다.
        // << dto 가져와서 INDIVIDUAL인지 확인하면 studentId 서로 비교하면 됨
        return TryCatchServiceTemplate.execute(() -> {
                    MaterialsDto dto = materialsRepository.getDetail(id);
                    String classroomId = dto.getClassroomId();

                    if(dto.getStatus() == MaterialsStatus.PUBLIC)
                        validationService.validateStudentAffiliated(classroomId, studentId);

                    if(dto.getStatus() == MaterialsStatus.INDIVIDUAL)
                        validationService.validateStudentIndividual(studentId, dto.getStudentId());

                    return dto;
                },
                null, DBErrorMsg.SELECT_ERROR);
    }

    public MaterialsPageResultDto getListByClassroom(String classroomId, int page){

        return TryCatchServiceTemplate.execute(() -> materialsRepository.getListByClassroom(classroomId, page),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public MaterialsPageResultDto getListForStudent(String classroomId, String studentId, int page){

        return TryCatchServiceTemplate.execute(() ->
                        materialsRepository.getListForStudent(classroomId, studentId, page),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public MaterialsPageResultDto getListForTeacher(String classroomId, int page, String teacherId){

        return TryCatchServiceTemplate.execute(() -> materialsRepository.getListForTeacher(classroomId, page),
                () -> validationService.validateTeacher(classroomId, teacherId), DBErrorMsg.SELECT_ERROR);
    }

    public MaterialsDto update(Long id, String studentId, String description, MultipartFile multipartFile,
                               Integer orderNum, MaterialsStatus status, String classroomId, String teacherId,
                               String title){

        return TryCatchServiceTemplate.execute(() -> tryCatchUpdate(id, studentId, description, multipartFile,
                        orderNum, status, title),
                () -> validationService.validateTeacher(classroomId, teacherId), DBErrorMsg.UPDATE_ERROR);
    }

    public void delete(Long id, String classroomId, String teacherId){

        TryCatchServiceTemplate.execute(() -> {

            fileManager.deleteFile(materialsRepository.getDetail(id).getMaterialUri());

            return null;
        },
        () -> validationService.validateTeacher(classroomId, teacherId), DBErrorMsg.DELETE_ERROR);

    }

    public File getFile(String materialUri, Long id, String userId, Role role)
            throws RuntimeException {

        MaterialsDto dto = materialsRepository.getDetail(id);

        if(role == Role.ROLE_STUDENT) {

            if(dto.getStatus() == MaterialsStatus.PUBLIC)
                validationService.validateStudentAffiliated(dto.getClassroomId(), userId);

            if(dto.getStatus() == MaterialsStatus.INDIVIDUAL)
                validationService.validateStudentIndividual(userId, dto.getStudentId());

            return fileManager.getFile(materialUri);
        }

        else if(role == Role.ROLE_TEACHER) {
            validationService.validateTeacher(userId, dto.getClassroomId());

            return fileManager.getFile(materialUri);
        }

        else
            throw new SecurityException(SecurityErrorMsg.ROLE_EXCEPTION.toString());
    }

    private MaterialsDto tryCatchUpload(String classroomId, String studentId, Integer orderNum, MaterialsStatus status,
                                        String description, MultipartFile multipartFile, String title
    ) throws RuntimeException, IOException {

        String filePath = fileManager.saveFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
        try {
            return materialsRepository.upload(classroomId, studentId, orderNum, status, title, description,
                    filePath);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    private MaterialsDto tryCatchUpdate(Long id, String studentId, String description, MultipartFile multipartFile,
                                        Integer orderNum, MaterialsStatus status, String title) {

        log.info("materialId = " + id);

        try {
            MaterialsDto dto = materialsRepository.getDetail(id);
            new File(dto.getMaterialUri()).delete();

            final String materialUri;
            if(multipartFile != null)
                materialUri = fileManager.saveFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
            else
                materialUri = null;

            return materialsRepository.update(id, studentId, orderNum, status,
                    description, materialUri, title);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
