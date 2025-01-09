package com.idealstudy.mvp.presentation.controller.classroom.inclass;

import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsPageResultDto;
import com.idealstudy.mvp.application.service.classroom.inclass.MaterialsService;
import com.idealstudy.mvp.enums.classroom.MaterialsStatus;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.security.annotation.ForStudent;
import com.idealstudy.mvp.security.annotation.ForTeacher;
import com.idealstudy.mvp.security.annotation.ForUser;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.util.TryCatchControllerTemplate;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MaterialsController {

    @Autowired
    private final MaterialsService materialsService;

    @ForTeacher
    @PostMapping(value = "/api/materials", consumes = "multipart/form-data")
    public ResponseEntity<MaterialsDto> upload(@RequestPart("dto") MaterialsDto dto, @RequestPart("file") MultipartFile file,
                                               HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        Integer orderNum = null;

        if(dto.getStatus() == MaterialsStatus.PUBLIC)
            return TryCatchControllerTemplate.execute(() -> materialsService.uploadPublic(
                teacherId, dto.getClassroomId(), dto.getDescription(), file, orderNum, dto.getTitle()
            ));
        else
            return TryCatchControllerTemplate.execute(() -> materialsService.uploadIndividual(
                    teacherId, dto.getClassroomId(), dto.getStudentId(), dto.getDescription(), file, orderNum
                    , dto.getTitle()));
    }

    @ForUser
    @GetMapping("/api/materials/{materialId}")
    public ResponseEntity<MaterialsDto> getDetails(@PathVariable Long materialId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String userId = payload.getSub();
        Role role = payload.getRole();

        if(role == Role.ROLE_TEACHER)
            return TryCatchControllerTemplate.execute(() ->
                    materialsService.getDetailForTeacher(materialId, userId));

        if(role == Role.ROLE_STUDENT)
            return TryCatchControllerTemplate.execute(() ->
                    materialsService.getDetailForStudent(materialId, userId));

        return new ResponseEntity<MaterialsDto>((MaterialsDto) null, HttpStatusCode.valueOf(400));
    }

    @ForStudent
    @GetMapping("/api/materials/student/{classroomId}")
    public ResponseEntity<MaterialsPageResultDto> getListForStudent(@PathVariable String classroomId,
                                                                    HttpServletRequest request,
                                                                    @RequestParam Integer page) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String studentId = payload.getSub();

        return TryCatchControllerTemplate.execute(() ->
                materialsService.getListForStudent(classroomId, studentId, page));
    }

    @GetMapping("/materials/classroom/{classroomId}")
    public ResponseEntity<MaterialsPageResultDto> getListByClassroom(@PathVariable String classroomId,
                                                                     @RequestParam Integer page) {

        return TryCatchControllerTemplate.execute(() ->
                materialsService.getListByClassroom(classroomId,page));
    }

    @ForTeacher
    @GetMapping("/api/materials/teacher/{classroomId}")
    public ResponseEntity<MaterialsPageResultDto> getListForTeacher(@PathVariable String classroomId,
                                                                    @RequestParam Integer page,
                                                                    HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return TryCatchControllerTemplate.execute(() ->
                materialsService.getListForTeacher(classroomId, page, teacherId));
    }

    // file 데이터를 포함해서 보내도 되고, 그렇지 않아도 되게 구현 가능.
    @ForTeacher
    @PatchMapping(value = "/api/materials/{materialId}", consumes = "multipart/form-data")
    public ResponseEntity<MaterialsDto> update(@PathVariable Long materialId, @RequestPart("dto") MaterialsDto dto,
                                               @RequestPart(value = "file", required = false) MultipartFile file,
                                               HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return TryCatchControllerTemplate.execute(() ->
                materialsService.update(materialId, dto.getStudentId(), dto.getDescription(), file,
                        null, dto.getStatus(), dto.getClassroomId(),teacherId, dto.getTitle()));
    }

    @ForTeacher
    @DeleteMapping("/api/materials/{materialId}/{classroomId}")
    public ResponseEntity<Object> delete(@PathVariable Long materialId, HttpServletRequest request,
                                         @PathVariable String classroomId) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> {
            materialsService.delete(materialId, classroomId, teacherId);
            return null;
        });
    }
    
    // 파일 전송 용
    @ForUser
    @GetMapping("/api/materials/download/{materialsId}")
    public ResponseEntity<Resource> downloadFile(@RequestParam String filePath,
                                                 @PathVariable Long materialsId,
                                                 HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String userId = payload.getSub();
        Role role = payload.getRole();

        try {
            File file = materialsService.getFile(filePath, materialsId, userId, role);
            Path path = Paths.get(file.getAbsolutePath());
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + file.getName() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("파일을 읽을 수 없습니다.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("파일 경로가 잘못되었습니다.", e);
        }
    }
}
