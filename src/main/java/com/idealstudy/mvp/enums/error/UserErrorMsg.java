package com.idealstudy.mvp.enums.error;

import com.idealstudy.mvp.error.ExceptionInfo;

public enum UserErrorMsg implements ExceptionInfo {

    DUPLICATION_ERROR("중복된 요청", 400),
    ALREADY_PROCEEDED("이미 처리된 요청",  400),
    ABNORMAL_REQUEST("비정상적인 요청: 워크플로우를 위반",  400),
    ILLEGAL_EMAIL("잘못된 이메일 주소입니다.", 400),
    DUPLICATION_EMAIL_REQUEST("현재 등록 중이거나 이미 등록된 이메일입니다.", 400),
    INVALID_CONTENT_TYPE("잘못된 확장자를 입력하셨습니다.", 400);

    private final String msg;
    private final int httpStatusCode;

    UserErrorMsg(String msg, int httpStatusCode) {
        this.msg = msg;
        this.httpStatusCode=httpStatusCode;
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
