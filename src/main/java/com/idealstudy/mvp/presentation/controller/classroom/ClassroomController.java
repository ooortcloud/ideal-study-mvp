package com.idealstudy.mvp.presentation.controller.classroom;

import com.idealstudy.mvp.application.dto.classroom.ClassroomPageResultDto;
import com.idealstudy.mvp.enums.classroom.ClassroomStatus;
import com.idealstudy.mvp.presentation.dto.classroom.ClassroomRequestDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.service.classroom.ClassroomService;
import java.util.List;
import java.util.Objects;

import com.idealstudy.mvp.security.annotation.ForStudent;
import com.idealstudy.mvp.security.annotation.ForTeacher;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.util.TryCatchControllerTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClassroomController {

    @Autowired
    private final ClassroomService service;

    @ForTeacher
    @PostMapping(value = "/api/classes", consumes = "multipart/form-data")
    public ResponseEntity<ClassroomResponseDto> createClass(@RequestPart("dto") ClassroomRequestDto dto,
                                                            @RequestPart("image") MultipartFile image,
                                                            HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> service.createClassroom(dto, teacherId, image.getInputStream()));
    }

    @GetMapping("/api/classes")
    public ResponseEntity<ClassroomPageResultDto> getAllClasses(@RequestParam int page,
                                                                @RequestParam ClassroomStatus status) {

        return TryCatchControllerTemplate.execute(() -> service.getAllClassrooms(page, status));
    }

    @ForStudent
    @GetMapping("/api/classes/student")
    public ResponseEntity<ClassroomPageResultDto> getClassesForStudent(@RequestParam int page,
                                                                @RequestParam ClassroomStatus status,
                                                                       HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String studentId = payload.getSub();

        // 구현 필요
        return TryCatchControllerTemplate.execute(() -> service.getAllClassrooms(page, status));
    }

    @ForTeacher
    @GetMapping("/api/classes/teacher")
    public ResponseEntity<ClassroomPageResultDto> getClassesForTeacher(@RequestParam Integer page,
                                                                       @RequestParam ClassroomStatus status,
                                                                       HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        // 구현 필요
        return TryCatchControllerTemplate.execute(() -> service.getAllClassrooms(page, status));
    }

    @GetMapping("/api/classes/{classId}")
    public ResponseEntity<ClassroomResponseDto> getClassById(@PathVariable String classId) {

        return TryCatchControllerTemplate.execute(() -> service.getClassroomById(classId));
    }

    @ForTeacher
    @PatchMapping(value = "/api/classes/{classId}", consumes = "multipart/form-data")
    public ResponseEntity<ClassroomResponseDto> updateClass(@PathVariable String classId,
                                                            @RequestPart(value = "dto", required = false) ClassroomRequestDto requestDto,
                                                            @RequestPart(value = "image", required = false) MultipartFile image,
                                                            HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> service.updateClassroom(classId, requestDto, teacherId,
                image.getInputStream()));
    }

    @ForTeacher
    @DeleteMapping("/api/classes/{classId}")
    public ResponseEntity<Object> deleteClass(@PathVariable String classId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> {
            service.deleteClassroom(classId, teacherId);
            return null;
        });
    }
}