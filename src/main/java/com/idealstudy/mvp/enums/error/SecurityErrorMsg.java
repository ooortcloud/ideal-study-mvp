package com.idealstudy.mvp.enums.error;


import com.idealstudy.mvp.error.ExceptionInfo;

public enum SecurityErrorMsg implements ExceptionInfo {

    ROLE_EXCEPTION("권한이 없습니다.", 403),
    PRIVATE_EXCEPTION("당신은 접근할 수 없는 private 컨텐츠 입니다.", 403),
    NOT_AFFILIATED("소속되어 있지 않습니다.", 403),
    NOT_YOURS("당신의 것이 아닙니다.", 403);

    private final String msg;
    private final int httpStatusCode;

    SecurityErrorMsg(String s, int httpStatusCode) {

        this.msg = s;
        this.httpStatusCode = httpStatusCode;
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
