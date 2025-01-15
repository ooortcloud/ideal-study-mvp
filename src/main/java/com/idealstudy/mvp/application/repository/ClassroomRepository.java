package com.idealstudy.mvp.application.repository;

import com.idealstudy.mvp.application.dto.classroom.ClassroomPageResultDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.enums.classroom.ClassroomStatus;


public interface ClassroomRepository {

    ClassroomResponseDto create(String title, String description, Integer capacity, String thumbnail, String teacherId);

    ClassroomResponseDto findById(String id); // ID로 수업 찾기

    ClassroomPageResultDto findList(int page, ClassroomStatus status); // 모든 수업 찾기

    /// 강사 소유의 클래스 조회
    public ClassroomPageResultDto findListForTeacher(int page, String teacherId, ClassroomStatus status);

    ClassroomResponseDto update(String id, String title, String description, Integer capacity, String thumbnail);

    public ClassroomResponseDto updateClassroomStatus(String id, ClassroomStatus status);

    void deleteById(String id); // 수업 삭제
}