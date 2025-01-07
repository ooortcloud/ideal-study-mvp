package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.infrastructure.jpa.entity.LikedEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.LikedJpaRepository;
import com.idealstudy.mvp.application.repository.LikedRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;


@Repository("likedClassroomRepositoryImpl")
@Slf4j
@RequiredArgsConstructor
public class LikedClassroomRepositoryImpl implements LikedRepository {

    @Autowired
    private final ClassroomJpaRepository classroomJpaRepository;

    @Autowired
    private final LikedJpaRepository likedJpaRepository;

    @Override
    public int create(Long  targetId) throws UnsupportedOperationException {

        throw new UnsupportedOperationException();
    }

    @Override
    public int create(String classroomId) throws NoSuchElementException {

        /*
            1. classroom 엔티티를 조회한다.
                - 조회되지 않은 경우: 바로 새롭게 만들어주면 끝.
            2. classroom 엔티티 조회에 성공하면, 다음의 행동들을 순차적으로 진행.
                - liked 테이블에 빈 liked 엔티티를 바로 저장한다.(createdBy, regDate 모두 자동으로 기입됨.)
                - JPA가 liked-classroom 연결 테이블에 연결 정보를 자동으로 저장해준다.
                - classroom 엔티티의 liked 리스트 객체는 자동 갱신이 안되므로, 해당 정보를 직접 갱신해준다.
            3. 본인에 의해 변경된 좋아요 수를 반환한다.
         */

        ClassroomEntity classroom = classroomJpaRepository.findById(classroomId).orElseThrow();

        LikedEntity liked = new LikedEntity();
        LikedEntity savedLiked = likedJpaRepository.save(liked);
        savedLiked.addClassroom(classroom);

        return likedJpaRepository.countByClassroomId(classroomId);
    }

    @Override
    public void delete(Long likedId, Long replyId) throws UnsupportedOperationException{

        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long likedId, String classroomId) {

        likedJpaRepository.findById(class);
    }

    @Override
    public int countById(Long classroomId) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();

    }

    @Override
    public int countById(String classroomId) {
        return likedJpaRepository.countByClassroomId(classroomId);
    }
}
