package com.idealstudy.mvp.application.dto.member;

import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDto extends MemberDto{

    public AdminDto(String userId, String password, String name, String phoneAddress, String email, Gender sex,
              byte[] profile, int fromSocial) {
        super(userId, password, name, phoneAddress, email, sex, "", 9999, Role.ROLE_ADMIN,
                "admin", profile, fromSocial, 0, 0);
    }

}
