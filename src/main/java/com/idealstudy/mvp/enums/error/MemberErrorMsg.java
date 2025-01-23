package com.idealstudy.mvp.enums.error;

import com.idealstudy.mvp.error.ExceptionInfo;

public enum MemberErrorMsg implements ExceptionInfo {

    NOT_REGISTERED_MEMBER("등록되지 않은 사용자", 500),
    ROLL_MISS_MATCH("알 수 없는 권한", 400);

    private String msg;
    private final int httpStatusCode;

    MemberErrorMsg(String msg, int httpStatusCode) {
        this.msg = msg;
        this.httpStatusCode = httpStatusCode;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    @Override
    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
