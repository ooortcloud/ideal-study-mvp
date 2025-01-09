package com.idealstudy.mvp.infrastructure.jpa.repository;

import com.idealstudy.mvp.infrastructure.jpa.entity.ReplyEntity;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyJpaRepository extends JpaRepository<ReplyEntity, Long> {

    Page<ReplyEntity> findByClassInquiry_id(Long id, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"likes"})
    Optional<ReplyEntity> findById(Long id);
}
