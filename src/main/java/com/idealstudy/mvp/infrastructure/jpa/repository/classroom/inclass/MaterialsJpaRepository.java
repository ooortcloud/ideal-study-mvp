package com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass;

import com.idealstudy.mvp.enums.classroom.MaterialsStatus;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.MaterialsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialsJpaRepository extends JpaRepository<MaterialsEntity, Long> {

    Page<MaterialsEntity> findByClassroom_classroomId(String classroomId, Pageable pageable);

    Page<MaterialsEntity> findByClassroom_classroomIdAndStudent_userIdOrStudent_userIdIsNull(String classroomId, String studentId,
                                                                       Pageable pageable);
    
    Page<MaterialsEntity> findByClassroom_classroomIdAndStatus(String classroomId, MaterialsStatus status,
                                                               Pageable pageable);
}
