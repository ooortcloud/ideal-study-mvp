package com.idealstudy.mvp.presentation.dto.member;

import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class MemberResponseDto {

    private String userId;

    private String name;

    private String phoneAddress;  // public에서는 null

    private String email;  // public에서는 null

    private Gender sex;

    private Integer level;

    private Role role;

    private String introduction;

    // 나중에 url로 바꾸고
    private byte[] profile;
}
