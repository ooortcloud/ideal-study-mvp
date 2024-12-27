package com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.SubmissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SubmissionJpaRepository extends JpaRepository<SubmissionEntity, Long> {

    Optional<SubmissionEntity> findByAssessment_idAndStudent_userId(
            @Param("id") Long assessmentId, @Param("userId") String studentId);

    Page<SubmissionEntity> findByStudent_userIdAndSubmissionTextIsNotNullOrSubmissionUriIsNotNull(@Param("userId") String studentId, Pageable pageable);

    Page<SubmissionEntity> findByAssessment_classroom_classroomId(String classroomId, Pageable pageable);
}
