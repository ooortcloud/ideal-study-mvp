package com.idealstudy.mvp.application.repository;

public interface LikedRepository {

    void create(Long targetId, String userId);

    void delete(Long likedId, Long targetId);

    /**
     *
     * @param targetId: 좋아요 대상 요소의 id(댓글 또는 클래스 id)
     * @return
     */
    int countById(Long targetId);
}
