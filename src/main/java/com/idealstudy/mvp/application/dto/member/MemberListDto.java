package com.idealstudy.mvp.application.dto.member;

import com.idealstudy.mvp.enums.member.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberListDto {

    private String userId;

    private String name;


    private Role role;

    // 나중에 uri로 변경해야 함
    private byte[] profile;
}
