package com.idealstudy.mvp.infrastructure.jpa.repository.classroom;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.PostEntity;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
    
}
