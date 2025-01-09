package com.idealstudy.mvp.security.dto;


import com.idealstudy.mvp.enums.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Builder
@Getter
@ToString
public class JwtPayloadDto {

    private final String sub;

    private final Role role;

    private final Date exp;

    private final Date iat;

    private final String name;

    private final int level;
}
