package com.idealstudy.mvp.enums.error;

import lombok.Getter;

@Getter
public enum DBErrorMsg {

    CREATE_ERROR("저장 실패"),
    SELECT_ERROR("조회 실패"),
    UPDATE_ERROR("수정 실패"),
    DELETE_ERROR("삭제 실패");

    private final String msg;
    
    DBErrorMsg(String s) {
        this.msg = s;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
