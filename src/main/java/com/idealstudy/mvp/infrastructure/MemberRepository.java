package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.dto.member.MemberPageResultDto;


public interface MemberRepository {

    void create(MemberDto dto);

    MemberDto findById(String id);

    MemberDto findByEmail(String email);

    MemberPageResultDto findMembers(PageRequestDto requestDto);

    MemberDto update(MemberDto dto);

    /**
     * 회원 탈퇴 시 DB에 완전히 제거하는 것이 아니라 상태값을 변경하는 것으로 처리한다.
     * @param id
     */
    boolean deleteById(String id);
}
