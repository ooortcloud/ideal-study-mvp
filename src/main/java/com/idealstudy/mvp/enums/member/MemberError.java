package com.idealstudy.mvp.enums.member;

public enum MemberError {

    NOT_REGISTERED_MEMBER("등록되지 않은 사용자"),
    ROLL_MISS_MATCH("알 수 없는 권한");

    private String msg;

    MemberError(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
