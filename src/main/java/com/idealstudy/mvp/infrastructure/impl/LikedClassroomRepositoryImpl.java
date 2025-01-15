package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.application.dto.LikedClassroomPageResultDto;
import com.idealstudy.mvp.application.dto.LikedReplyPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.LikedEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.LikedJpaRepository;
import com.idealstudy.mvp.application.repository.LikedRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;


@Repository("likedClassroomRepositoryImpl")
@Slf4j
@RequiredArgsConstructor
public class LikedClassroomRepositoryImpl implements LikedRepository {

    @Autowired
    private final ClassroomJpaRepository classroomJpaRepository;

    @Autowired
    private final LikedJpaRepository likedJpaRepository;

    @Override
    public long create(Long  targetId) throws UnsupportedOperationException {

        throw new UnsupportedOperationException();
    }

    @Override
    public long create(String classroomId) throws NoSuchElementException {

        /*
        LikedEntity liked = new LikedEntity();
        LikedEntity savedLiked = likedJpaRepository.save(liked);

        ClassroomEntity classroom = classroomJpaRepository.findById(classroomId).orElseThrow();

        ClassroomLikedEntity entity = ClassroomLikedEntity.builder()
                .liked(savedLiked)
                .classroom(classroom)
                .build();

        classroomLikedJpaRepository.save(entity);

        // 최종 개수를 카운트해서 반환
        return (int) classroomLikedJpaRepository.countByClassroom_classroomId(classroomId);

         */

        ClassroomEntity classroom = classroomJpaRepository.findById(classroomId).orElseThrow();

        LikedEntity liked = LikedEntity.builder()
                .classroom(classroom)
                .build();

        LikedEntity savedLiked = likedJpaRepository.save(liked);

        return savedLiked.getLikedId();
    }

    public String getCreatedBy(Long likedId) throws NoSuchElementException {

        return likedJpaRepository.findById(likedId).orElseThrow().getCreatedBy();
    }

    @Override
    public LikedReplyPageResultDto findLikedList(Long replyId) throws Exception {

        throw new UnsupportedOperationException();
    }

    @Override
    public LikedClassroomPageResultDto findLikedList(String classroomId) throws Exception {
        return null;
    }

    @Override
    public boolean checkAlreadyLiked(String userId, String classroomId) {

        Optional<LikedEntity> entity = likedJpaRepository.findByClassroom_classroomIdAndCreatedBy(classroomId, userId);

        return entity.isPresent();
    }

    @Override
    public boolean checkAlreadyLiked(String userId, Long replyId) throws UnsupportedOperationException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long likedId) throws NoSuchElementException {

        LikedEntity entity = likedJpaRepository.findById(likedId).orElseThrow();
        likedJpaRepository.delete(entity);
    }

    @Override
    public int countById(Long classroomId) throws UnsupportedOperationException {

        throw new UnsupportedOperationException();
    }

    @Override
    public int countById(String classroomId) {

        return likedJpaRepository.countByClassroom_classroomId(classroomId);
    }
}
