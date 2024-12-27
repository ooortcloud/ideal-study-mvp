package com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass;

import com.idealstudy.mvp.enums.classroom.AssessmentType;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.AssessmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentJpaRepository extends JpaRepository<AssessmentEntity, Long> {

    Page<AssessmentEntity> findByClassroom_classroomId(String classroomId, Pageable pageable);

    Page<AssessmentEntity> findByClassroom_classroomIdAndAssessmentTypeAndFeedbackToAllIsNotNull(
            String classroomId, AssessmentType assessmentType, Pageable pageable);
}

