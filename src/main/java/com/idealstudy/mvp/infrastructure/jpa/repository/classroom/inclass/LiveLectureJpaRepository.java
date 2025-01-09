package com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.LiveLectureEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveLectureJpaRepository extends JpaRepository<LiveLectureEntity, Long> {

    Page<LiveLectureEntity> findByClassroom_classroomId(String classroomId, Pageable pageable);
}
