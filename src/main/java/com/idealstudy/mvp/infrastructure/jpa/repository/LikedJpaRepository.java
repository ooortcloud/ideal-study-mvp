package com.idealstudy.mvp.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.idealstudy.mvp.infrastructure.jpa.entity.LikedEntity;

public interface LikedJpaRepository extends JpaRepository<LikedEntity, Long> {

    // @ManyToMany로 인해 자동 생성되는 연결 테이블에 간접적으로 접근.(엔티티 객체가 없어서 직접 접근 불가.)
    @Query("SELECT COUNT(l) FROM LikedEntity l JOIN l.replies r WHERE r.id = :targetId")
    int countByReplyId(@Param("targetId") Long targetId);

    // 아쉽게도 객체 그래프 탐색 코드 부분에서 동적 처리는 불가함.
    @Query("SELECT COUNT(l) FROM LikedEntity l JOIN l.classrooms c WHERE c.id = :targetId") 
    int countByClassroomId(@Param("targetId") Long targetId);
}
