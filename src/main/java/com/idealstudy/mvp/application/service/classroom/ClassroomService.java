package com.idealstudy.mvp.application.service.classroom;

import com.idealstudy.mvp.infrastructure.ClassroomRepository;
import com.idealstudy.mvp.presentation.dto.classroom.ClassroomRequestDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    public ClassroomResponseDto createClassroom(ClassroomRequestDto request) {
        ClassroomEntity savedClassroom = classroomRepository.save(request.toEntity());
        return ClassroomResponseDto.fromEntity(savedClassroom);
    }

    public List<ClassroomResponseDto> getAllClassrooms() {
        return classroomRepository.findAll().stream()
                .map(ClassroomResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ClassroomResponseDto getClassroomById(String id) {
        return classroomRepository.findById(id)
                .map(ClassroomResponseDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
    }

    public ClassroomResponseDto updateClassroom(String id, ClassroomRequestDto request) {
        ClassroomEntity classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
        request.updateEntity(classroom);
        ClassroomEntity updatedClassroom = classroomRepository.save(classroom);
        return ClassroomResponseDto.fromEntity(updatedClassroom);
    }

    public void deleteClassroom(String id) {
        if (!classroomRepository.existsById(id)) {
            throw new IllegalArgumentException("Classroom not found");
        }
        classroomRepository.deleteById(id);
    }
}
