package com.idealstudy.mvp.application.repository;

/**
 * id 타입이 동일했으면 완벽한 설계였는데,
 * 설계 미스로 id 타입이 달라지게 되면서 인터페이스가 꼬였다.
 */
public interface LikedRepository {

    int create(Long targetId) throws Exception;
    int create(String targetId) throws Exception;

    void delete(Long likedId, Long targetId) throws Exception;
    void delete(Long likedId, String targetId) throws Exception;

    /**
     *
     * @param targetId: 좋아요 대상 요소의 id(댓글 또는 클래스 id)
     * @return
     */
    int countById(Long targetId) throws Exception;
    int countById(String targetId) throws Exception;
}
