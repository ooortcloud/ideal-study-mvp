package com.idealstudy.mvp.enums.error;


public enum SecurityErrorMsg {

    ROLE_EXCEPTION("권한이 없습니다."),
    PRIVATE_EXCEPTION("당신은 접근할 수 없는 private 컨텐츠 입니다."),
    NOT_AFFILIATED("소속되어 있지 않습니다."),
    NOT_YOURS("당신의 것이 아닙니다.");


    private final String msg;

    SecurityErrorMsg(String s) {

        this.msg = s;
    }

    @Override
    public String toString() {
        return msg;
    }
}
