package com.idealstudy.mvp.infrastructure.jpa.repository.classroom;

import com.idealstudy.mvp.enums.classroom.ClassroomStatus;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomJpaRepository extends JpaRepository<ClassroomEntity, String> {

    Page<ClassroomEntity> findByStatus(ClassroomStatus status, Pageable pageable);

    Page<ClassroomEntity> findByTeacher_userId(String teacherId, Pageable pageable);

    Page<ClassroomEntity> findByTeacher_userIdAndStatus(String teacherId, ClassroomStatus status, Pageable pageable);


    // 학생 목록 조회: 반대 방향 엔티티로부터 어떻게 역으로 조회할 수 있는지?
}