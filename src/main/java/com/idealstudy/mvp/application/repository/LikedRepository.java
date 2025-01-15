package com.idealstudy.mvp.application.repository;

import com.idealstudy.mvp.application.dto.LikedClassroomPageResultDto;
import com.idealstudy.mvp.application.dto.LikedReplyPageResultDto;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * 이 인터페이스는 정확히는 Liked 테이블에 대한 연결 테이블을 동시에 작업하는 것을 추상화한 것이다.
 * 좋아요 테이블 자체에 대한 기능은 현재 요구사항에 없을 뿐더러,
 * 앞으로도 좋아요 기능 특성 상 연결 테이블 선에서만 기능이 요구될 것이기 때문이다.
 *
 * id 타입이 동일했으면 메소드 오버로딩을 사용하지 않아도 됐을텐데, 이것은 설계 결함에 의한 것이라 아쉽다.
 */
public interface LikedRepository {

    /*
    int create(Long replyId) throws Exception;
    int create(String classroomId) throws Exception;
     */

    /// 생성된 LikedId를 반환
    long create(Long replyId) throws Exception;
    long create(String classroomId) throws Exception;

    String getCreatedBy(Long likedId) throws NoSuchElementException;

    LikedReplyPageResultDto findLikedList(Long replyId) throws Exception;
    LikedClassroomPageResultDto findLikedList(String classroomId) throws Exception;

    void delete(Long likedId) throws Exception;

    boolean checkAlreadyLiked(String userId, String classroomId) throws Exception;

    boolean checkAlreadyLiked(String userId, Long replyId) throws Exception;

    int countById(Long replyId) throws Exception;
    int countById(String classroomId) throws Exception;
}
