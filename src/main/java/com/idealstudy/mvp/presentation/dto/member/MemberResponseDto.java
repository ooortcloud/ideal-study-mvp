package com.idealstudy.mvp.presentation.dto.member;

import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private String userId;

    private String name;

    private String phoneAddress;  // public에서는 null

    private String email;  // public에서는 null

    private Gender sex;

    private Integer level;

    private Role role;

    private String introduction;

    private String profileUri;
}
