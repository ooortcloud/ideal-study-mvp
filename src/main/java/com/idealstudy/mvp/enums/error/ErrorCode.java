package com.idealstudy.mvp.enums.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    DUPLICATION_ERROR("중복된 요청", 1000, 400);

    private final String msg;
    private final int statusCode;
    private final int httpStatusCode;

    ErrorCode(String msg, int statusCode, int httpStatusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
        this.httpStatusCode=httpStatusCode;
    }
}
