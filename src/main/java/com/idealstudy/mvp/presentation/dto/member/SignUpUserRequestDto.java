package com.idealstudy.mvp.presentation.dto.member;

import com.idealstudy.mvp.enums.member.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpUserRequestDto {

    private String email;

    private Role role;
}
