package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.application.dto.LikedClassroomPageResultDto;
import com.idealstudy.mvp.application.dto.LikedReplyPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.ClassroomLikedEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.LikedEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.ClassroomLikedJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.LikedJpaRepository;
import com.idealstudy.mvp.application.repository.LikedRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;


@Repository("likedClassroomRepositoryImpl")
@Slf4j
@RequiredArgsConstructor
public class LikedClassroomRepositoryImpl implements LikedRepository {

    @Autowired
    private final ClassroomJpaRepository classroomJpaRepository;

    @Autowired
    private final LikedJpaRepository likedJpaRepository;

    @Autowired
    private final ClassroomLikedJpaRepository classroomLikedJpaRepository;

    @Override
    public int create(Long  targetId) throws UnsupportedOperationException {

        throw new UnsupportedOperationException();
    }

    @Override
    public int create(String classroomId) throws NoSuchElementException {

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
    public void delete(Long likedId, Long replyId) throws UnsupportedOperationException{

        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long likedId, String classroomId) {

         ClassroomLikedEntity findEntity = classroomLikedJpaRepository
                 .findByClassroom_classroomIdAndLiked_likedId(classroomId, likedId).orElseThrow();

         classroomLikedJpaRepository.delete(findEntity);

         likedJpaRepository.delete(findEntity.getLiked());
    }

    @Override
    public int countById(Long classroomId) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();

    }

    @Override
    public int countById(String classroomId) {

        // return likedJpaRepository.countByClassroomId(classroomId);

        return (int) classroomLikedJpaRepository.countByClassroom_classroomId(classroomId);
    }
}
