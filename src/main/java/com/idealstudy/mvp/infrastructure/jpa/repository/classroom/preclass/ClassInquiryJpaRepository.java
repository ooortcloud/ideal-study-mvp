package com.idealstudy.mvp.infrastructure.jpa.repository.classroom.preclass;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.preclass.ClassInquiryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassInquiryJpaRepository extends JpaRepository<ClassInquiryEntity, Long> {

    Page<ClassInquiryEntity> findByClassroom_classroomId(String classroomId, Pageable pageable);

    Page<ClassInquiryEntity> findByCreatedBy(String createdBy, Pageable pageable);
}
