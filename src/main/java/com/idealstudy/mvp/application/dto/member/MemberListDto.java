package com.idealstudy.mvp.application.dto.member;

import com.idealstudy.mvp.enums.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class MemberListDto {

    private String userId;

    private String name;

    private Role role;

    private String profileUri;
}
