package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.enums.member.Role;

public interface EmailRepository {

    void addToken(String email, Role role, String token);

    String getToken(String email);

    Boolean deleteToken(String email);
}
