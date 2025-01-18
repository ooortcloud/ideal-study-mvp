package com.idealstudy.mvp.enums.error;

import com.idealstudy.mvp.error.ExceptionInfo;

public enum SystemErrorMsg implements ExceptionInfo {

    IO_EXCEPTION("시스템 내부 입출력 오류", 500),
    ILLEGAL_ARGUMENT_EXCEPTION("허용할 수 없는 인자", 400);

    private final String msg;
    private final int httpStatusCode;

    SystemErrorMsg(String msg, int httpStatusCode) {
        this.msg = msg;
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
