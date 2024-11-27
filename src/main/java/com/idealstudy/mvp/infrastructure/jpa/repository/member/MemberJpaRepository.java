package com.idealstudy.mvp.infrastructure.jpa.repository.member;

import com.idealstudy.mvp.infrastructure.jpa.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, String> {

    MemberEntity findByEmail(String email);
}
