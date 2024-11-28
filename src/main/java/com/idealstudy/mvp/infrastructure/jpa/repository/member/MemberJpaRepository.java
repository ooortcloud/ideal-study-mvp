package com.idealstudy.mvp.infrastructure.jpa.repository.member;

import com.idealstudy.mvp.infrastructure.jpa.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, String> {

    @Query(value = "SELECT m FROM Member m WHERE m.email = :email")
    MemberEntity findByEmail(String email);
}
