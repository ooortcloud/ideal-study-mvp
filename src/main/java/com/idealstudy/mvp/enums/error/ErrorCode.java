package com.idealstudy.mvp.enums.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    DUPLICATION_ERROR("중복된 요청", 1000, 400),
    ALREADY_PROCEEDED("이미 처리된 요청", 1001, 400),
    ABNORMAL_REQUEST("비정상적인 요청: 워크플로우를 위반", 1002, 400);

    private final String msg;
    private final int statusCode;
    private final int httpStatusCode;

    ErrorCode(String msg, int statusCode, int httpStatusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
        this.httpStatusCode=httpStatusCode;
    }
}
