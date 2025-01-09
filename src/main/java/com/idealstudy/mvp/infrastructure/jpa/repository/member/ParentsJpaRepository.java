package com.idealstudy.mvp.infrastructure.jpa.repository.member;

import com.idealstudy.mvp.infrastructure.jpa.entity.member.ParentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentsJpaRepository extends JpaRepository<ParentsEntity, String> {
}
