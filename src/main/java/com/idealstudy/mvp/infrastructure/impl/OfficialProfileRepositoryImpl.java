package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.infrastructure.OfficialProfileRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.OfficialProfileEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.OfficialProfileJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OfficialProfileRepositoryImpl implements OfficialProfileRepository {

    @Autowired
    private final OfficialProfileJpaRepository officialProfileJpaRepository;

    @Override
    public void create(String teacherId) {

    }

    @Override
    public OfficialProfileDto findByTeacherId(String teacherId) {
        return null;
    }

    @Override
    public void update(String teacherId, String contents) {

    }

    // @Override
    public OfficialProfileDto findById(Long id) {
        OfficialProfileEntity entity = officialProfileJpaRepository.findById(id).orElseThrow();
        return entity.toDto();
    }
}
