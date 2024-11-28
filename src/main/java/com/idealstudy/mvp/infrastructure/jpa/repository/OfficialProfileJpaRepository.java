package com.idealstudy.mvp.infrastructure.jpa.repository;

import com.idealstudy.mvp.infrastructure.jpa.entity.OfficialProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficialProfileJpaRepository extends JpaRepository<OfficialProfileEntity, Long> {
}
