package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import java.util.List;
import java.util.Optional;


public interface ClassroomRepository {

    ClassroomEntity save(ClassroomEntity entity); // 생성 및 업데이트를 위한 메서드

    Optional<ClassroomEntity> findById(String id); // ID로 수업 찾기

    List<ClassroomEntity> findAll(); // 모든 수업 찾기

    void deleteById(String id); // 수업 삭제

    boolean existsById(String id); // 수업 존재 여부 확인
}