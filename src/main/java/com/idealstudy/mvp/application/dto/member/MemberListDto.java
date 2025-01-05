package com.idealstudy.mvp.application.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberListDto {

    private String userId;

    private String name;

    // 나중에 uri로 변경해야 함
    private byte[] profile;
}
