package com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass;

import com.idealstudy.mvp.enums.classroom.EnrollmentStatus;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.EnrollmentEntity;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentEntity, Long> {

    Page<EnrollmentEntity> findByClassroom_ClassroomId(String classroomId, Pageable pageable);

    Optional<EnrollmentEntity> findByClassroom_ClassroomIdAndStudent_UserIdAndStatus(String classroomId, String userId,
                                                                                     EnrollmentStatus status);

    Page<EnrollmentEntity> findByCreatedBy(@Param("createdBy") String applicantId, Pageable pAgeable);
}
