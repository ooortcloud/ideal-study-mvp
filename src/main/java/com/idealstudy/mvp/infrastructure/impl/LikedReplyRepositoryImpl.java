package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.application.dto.LikedClassroomPageResultDto;
import com.idealstudy.mvp.application.dto.LikedReplyPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.LikedEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.ReplyEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.LikedJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.ReplyJpaRepository;
import com.idealstudy.mvp.application.repository.LikedRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.ReplyLikedJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("likedReplyRepositoryImpl")
@Slf4j
@RequiredArgsConstructor
public class LikedReplyRepositoryImpl implements LikedRepository {

    @Autowired
    private final LikedJpaRepository likedJpaRepository;

    @Autowired
    private final ReplyJpaRepository replyJpaRepository;

    @Autowired
    private final ReplyLikedJpaRepository replyLikedJpaRepository;

    @Override
    public int create(Long replyId) {



        return -1;
    }

    @Override
    public int create(String targetId) {

        throw new UnsupportedOperationException();
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
    public void delete(Long likedId, Long replyId) {
        log.info("좋아요 제거");

        LikedEntity entity = likedJpaRepository.findById(likedId).orElseThrow();
        likedJpaRepository.delete(entity);
        ReplyEntity replyEntity = replyJpaRepository.findById(replyId).orElseThrow();
        replyJpaRepository.save(replyEntity);
    }

    @Override
    public void delete(Long likedId, String targetId) {

        throw new UnsupportedOperationException();
    }

    @Override
    public int countById(Long replyId) {


        // return likedJpaRepository.countByReplyId(replyId);

        return (int) replyLikedJpaRepository.countByReply_commentId(replyId);
    }

    @Override
    public int countById(String targetId) {
        throw new UnsupportedOperationException();
    }
}
