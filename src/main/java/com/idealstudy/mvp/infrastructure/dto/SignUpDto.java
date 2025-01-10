package com.idealstudy.mvp.infrastructure.dto;

import com.idealstudy.mvp.enums.member.Role;
import lombok.*;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    private String email;

    private Role role;
}
