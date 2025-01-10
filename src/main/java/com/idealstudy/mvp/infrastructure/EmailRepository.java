package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.dto.SignUpDto;

public interface EmailRepository {

    SignUpDto addToken(String token, String email, Role role);

    SignUpDto getToken(String token)
            throws IllegalArgumentException;

    Boolean deleteToken(String token);
}
