package com.idealstudy.mvp.infrastructure.jpa.repository.member;

import com.idealstudy.mvp.infrastructure.jpa.entity.member.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentJpaRepository extends JpaRepository<StudentEntity, String> {
}
