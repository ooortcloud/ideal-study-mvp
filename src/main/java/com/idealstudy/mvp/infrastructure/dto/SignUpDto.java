package com.idealstudy.mvp.infrastructure.dto;

import com.idealstudy.mvp.enums.member.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class SignUpDto {

    private String email;

    private Role role;
}
