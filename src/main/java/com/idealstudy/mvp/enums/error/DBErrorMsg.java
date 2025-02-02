package com.idealstudy.mvp.enums.error;

import com.idealstudy.mvp.error.ExceptionInfo;
import lombok.Getter;

@Getter
public enum DBErrorMsg implements ExceptionInfo {

    CREATE_ERROR("저장 실패", 500),
    SELECT_ERROR("조회 실패", 500),
    UPDATE_ERROR("수정 실패", 500),
    DELETE_ERROR("삭제 실패", 500);

    private final String msg;

    private final int httpStatusCode;
    
    DBErrorMsg(String s, int httpStatusCode) {
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
