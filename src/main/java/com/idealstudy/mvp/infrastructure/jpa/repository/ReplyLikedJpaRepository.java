package com.idealstudy.mvp.infrastructure.jpa.repository;

import com.idealstudy.mvp.infrastructure.jpa.entity.ClassroomLikedEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.ReplyLikedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReplyLikedJpaRepository extends JpaRepository<ReplyLikedEntity, Long> {

    long countByReply_commentId(@Param("commentId") Long replyId);

    Optional<ClassroomLikedEntity> findByReply_commentIdAndLiked_likedId(@Param("commentId") Long replyId, Long likedId);
}
