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
    public void create(Long replyId, String userId) {

        log.info("좋아요 추가");
        ReplyEntity replyEntity = replyJpaRepository.findById(replyId).orElseThrow();

        LikedEntity newEntity = LikedEntity.builder()
                .createdBy(userId)
                .build();

        newEntity.addReply(replyEntity);

        // @ManyToMany에 의해 ReplyEntity는 연관 테이블에 자동으로 매핑됨.
        likedJpaRepository.save(newEntity);
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
    public int countById(Long replyId) {
        return likedJpaRepository.countByReplyId(replyId);
    }
}
