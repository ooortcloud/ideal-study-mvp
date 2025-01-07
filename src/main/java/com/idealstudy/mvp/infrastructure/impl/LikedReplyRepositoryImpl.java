package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.infrastructure.jpa.entity.LikedEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.ReplyEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.LikedJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.ReplyJpaRepository;
import com.idealstudy.mvp.application.repository.LikedRepository;
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

    @Override
    public int create(Long replyId) {

        log.info("좋아요 추가");
        ReplyEntity reply = replyJpaRepository.findById(replyId).orElseThrow();

        LikedEntity liked = new LikedEntity();
        // @ManyToMany에 의해 ReplyEntity는 연관 테이블에 자동으로 매핑됨.
        LikedEntity savedLikedEntity = likedJpaRepository.save(liked);
        savedLikedEntity.addReply(reply);

        return -1;
    }

    @Override
    public int create(String targetId) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long likedId, Long replyId) {
        log.info("좋아요 제거");

        LikedEntity entity = likedJpaRepository.findById(likedId).orElseThrow();
        likedJpaRepository.delete(entity);
        ReplyEntity replyEntity = replyJpaRepository.findById(replyId).orElseThrow();
        replyEntity.getLikes().remove(entity);
        replyJpaRepository.save(replyEntity);
    }

    @Override
    public void delete(Long likedId, String targetId) {

        throw new UnsupportedOperationException();
    }

    @Override
    public int countById(Long replyId) {
        return likedJpaRepository.countByReplyId(replyId);
    }

    @Override
    public int countById(String targetId) {
        throw new UnsupportedOperationException();
    }
}
