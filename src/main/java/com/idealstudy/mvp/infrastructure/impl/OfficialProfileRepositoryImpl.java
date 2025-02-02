package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.application.repository.OfficialProfileRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.OfficialProfileEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.OfficialProfileJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.member.TeacherJpaRepository;
import com.idealstudy.mvp.mapstruct.OfficialProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OfficialProfileRepositoryImpl implements OfficialProfileRepository {

    @Autowired
    private final OfficialProfileJpaRepository officialProfileJpaRepository;

    @Autowired
    private final TeacherJpaRepository teacherJpaRepository;

    @Override
    public OfficialProfileDto create(String teacherId, String initContent) {

        TeacherEntity teacherEntity = teacherJpaRepository.findById(teacherId).orElseThrow();

        OfficialProfileEntity entity = OfficialProfileEntity.builder()
                .teacher(teacherEntity)
                .id(teacherId)
                .content(initContent)
                .build();

        return OfficialProfileMapper.INSTANCE.toDto(officialProfileJpaRepository.save(entity));
    }

    @Override
    public OfficialProfileDto findByTeacherId(String teacherId) {

        OfficialProfileEntity entity = officialProfileJpaRepository.findById(teacherId).orElseThrow();

        return entity.toDto();
    }

    @Override
    public OfficialProfileDto update(String teacherId, String html) {

        OfficialProfileEntity entity = officialProfileJpaRepository.findById(teacherId).orElseThrow();
        entity.setContent(html);
        return officialProfileJpaRepository.save(entity).toDto();
    }
}
