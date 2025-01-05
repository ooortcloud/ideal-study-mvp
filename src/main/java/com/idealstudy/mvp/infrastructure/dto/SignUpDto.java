package com.idealstudy.mvp.infrastructure.dto;

import com.idealstudy.mvp.enums.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Builder
public class SignUpDto {

    private String token;

    private Role role;
}
