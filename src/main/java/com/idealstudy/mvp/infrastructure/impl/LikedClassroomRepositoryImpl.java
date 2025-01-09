package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.infrastructure.jpa.repository.LikedJpaRepository;
import com.idealstudy.mvp.application.repository.LikedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("likedClassroomRepositoryImpl")
@Slf4j
@RequiredArgsConstructor
public class LikedClassroomRepositoryImpl implements LikedRepository {

    @Autowired
    private final LikedJpaRepository likedJpaRepository;

    @Override
    public void create(Long targetId, String userId) {

    }

    @Override
    public void delete(Long likedId, Long targetId) {

    }

    @Override
    public int countById(Long classroomId) {
        return likedJpaRepository.countByClassroomId(classroomId);
    }
}
