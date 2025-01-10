package com.idealstudy.mvp.infrastructure.jpa.repository;

import com.idealstudy.mvp.infrastructure.jpa.entity.ClassroomLikedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassroomLikedJpaRepository extends JpaRepository<ClassroomLikedEntity, Long> {

    long countByClassroom_classroomId(String classroomId);

    Optional<ClassroomLikedEntity> findByClassroom_classroomIdAndLiked_likedId(String classroomId, Long likedId);
}
