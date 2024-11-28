package com.idealstudy.mvp.infrastructure.impl.member;

import com.idealstudy.mvp.infrastructure.TeacherRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.member.TeacherJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherRepositoryImpl implements TeacherRepository {

    private TeacherJpaRepository teacherJpaRepository;

    // repository 실제 메서드 구현

}
