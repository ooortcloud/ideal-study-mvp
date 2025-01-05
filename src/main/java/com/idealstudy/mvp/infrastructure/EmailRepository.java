package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.dto.SignUpDto;

public interface EmailRepository {

    void addToken(String email, Role role, String token);

    SignUpDto getToken(String email);

    Boolean deleteToken(String email);
}
