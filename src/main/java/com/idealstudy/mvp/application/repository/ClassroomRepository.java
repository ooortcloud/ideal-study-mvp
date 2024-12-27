package com.idealstudy.mvp.application.repository;

import com.idealstudy.mvp.application.dto.classroom.ClassroomPageResultDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import java.util.List;
import java.util.Optional;


public interface ClassroomRepository {

    ClassroomResponseDto save(String title, String description, Integer capacity, String thumbnail);

    ClassroomResponseDto findById(String id); // ID로 수업 찾기

    ClassroomPageResultDto findAll(); // 모든 수업 찾기

    ClassroomResponseDto update(String id, String title, String description, Integer capacity, String thumbnail);

    void deleteById(String id); // 수업 삭제

    ClassroomPageResultDto findByStudentId(String studentId);
}