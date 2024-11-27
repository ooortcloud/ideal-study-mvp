package com.idealstudy.mvp.presentation.controller.classroom;

import com.idealstudy.mvp.presentation.dto.classroom.ClassroomRequestDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.service.classroom.ClassroomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClassroomController {

    private final ClassroomService service;

    @PostMapping("/api/classes")
    public ResponseEntity<ClassroomResponseDto> createClass(@RequestBody ClassroomRequestDto request) {
        return ResponseEntity.ok(service.createClassroom(request));
    }

    @GetMapping("/api/classes")
    public ResponseEntity<List<ClassroomResponseDto>> getAllClasses() {
        return ResponseEntity.ok(service.getAllClassrooms());
    }

    @GetMapping("/api/classes/{classId}")
    public ResponseEntity<ClassroomResponseDto> getClassById(@PathVariable String classId) {
        return ResponseEntity.ok(service.getClassroomById(classId));
    }

    @PatchMapping("/api/classes/{classId}")
    public ResponseEntity<ClassroomResponseDto> updateClass(@PathVariable String classId, @RequestBody ClassroomRequestDto request) {
        return ResponseEntity.ok(service.updateClassroom(classId, request));
    }

    @DeleteMapping("/api/classes/{classId}")
    public ResponseEntity<Void> deleteClass(@PathVariable String classId) {
        service.deleteClassroom(classId);
        return ResponseEntity.noContent().build();
    }

}