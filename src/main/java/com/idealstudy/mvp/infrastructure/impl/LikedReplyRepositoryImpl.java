package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.application.dto.LikedClassroomPageResultDto;
import com.idealstudy.mvp.application.dto.LikedReplyPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.LikedEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.ReplyEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.LikedJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.ReplyJpaRepository;
import com.idealstudy.mvp.application.repository.LikedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;


@Repository("likedReplyRepositoryImpl")
@Slf4j
@RequiredArgsConstructor
public class LikedReplyRepositoryImpl implements LikedRepository {

    @Autowired
    private final LikedJpaRepository likedJpaRepository;

    @Autowired
    private final ReplyJpaRepository replyJpaRepository;

    @Override
    public long create(Long replyId) {

        ReplyEntity reply = replyJpaRepository.findById(replyId).orElseThrow();

        LikedEntity likedEntity = LikedEntity.builder()
                .reply(reply)
                .build();

        LikedEntity result = likedJpaRepository.save(likedEntity);

        return result.getLikedId();
    }

    @Override
    public long create(String targetId) throws UnsupportedOperationException {

        throw new UnsupportedOperationException();
    }

    @Override
    public String getCreatedBy(Long likedId) throws NoSuchElementException {

        return likedJpaRepository.findById(likedId).orElseThrow().getCreatedBy();
    }

    @Override
    public LikedReplyPageResultDto findLikedList(Long replyId) throws Exception {
        return null;
    }

    @Override
    public LikedClassroomPageResultDto findLikedList(String classroomId) throws Exception {

        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long likedId) throws NoSuchElementException {

        LikedEntity entity = likedJpaRepository.findById(likedId).orElseThrow();
        likedJpaRepository.delete(entity);
    }

    @Override
    public boolean checkAlreadyLiked(String userId, String classroomId) throws Exception {
        return false;
    }

    @Override
    public boolean checkAlreadyLiked(String userId, Long replyId) throws Exception {
        return false;
    }

    @Override
    public int countById(Long replyId) {

        return likedJpaRepository.countByReply_commentId(replyId);
    }

    @Override
    public int countById(String targetId) {
        throw new UnsupportedOperationException();
    }
}
