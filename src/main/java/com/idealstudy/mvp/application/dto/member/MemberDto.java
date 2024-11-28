package com.idealstudy.mvp.application.dto.member;

import com.idealstudy.mvp.enums.member.*;
import lombok.*;

@Data
@Builder
public class MemberDto {

    private String userId;

    private String password;

    private String name;

    private String phoneAddress;

    private String email;

    private Gender sex;

    private String referralId;

    private Integer level;

    private Role role;

    private String introduction;

    private byte[] profile;

    private int fromSocial;

    private int init;

    private int deleted;

    public boolean fromSocial() {
        return fromSocial == 1;
    }

    public boolean isFirst() {
        return init == 1;
    }

    public boolean isDeleted() {
        return deleted == 1;
    }

    public void setFirst(boolean b) {
        init = b ? 1 : 0;
    }

    public void setDeleted(boolean b) {
        deleted = b ? 1 : 0;
    }
}
