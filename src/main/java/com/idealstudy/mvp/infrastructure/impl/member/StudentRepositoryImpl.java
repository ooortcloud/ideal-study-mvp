package com.idealstudy.mvp.infrastructure.impl.member;

import com.idealstudy.mvp.infrastructure.StudentRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.member.StudentJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private StudentJpaRepository studentJpaRepository;

    // repository 실제 메서드 구현

}
