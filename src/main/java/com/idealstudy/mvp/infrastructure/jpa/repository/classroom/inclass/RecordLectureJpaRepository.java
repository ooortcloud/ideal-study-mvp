package com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.RecordLectureEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordLectureJpaRepository extends JpaRepository<RecordLectureEntity, Long> {

    Page<RecordLectureEntity> findByClassroom_ClassroomId(String classroomId, Pageable pageable);
}
