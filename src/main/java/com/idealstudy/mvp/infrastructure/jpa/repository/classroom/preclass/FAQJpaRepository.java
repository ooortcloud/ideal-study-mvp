package com.idealstudy.mvp.infrastructure.jpa.repository.classroom.preclass;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.preclass.FAQEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQJpaRepository extends JpaRepository<FAQEntity, Long> {

    Page<FAQEntity> findByClassroom_classroomId(String classroomId, Pageable pageable);
}
