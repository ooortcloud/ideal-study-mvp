package com.idealstudy.mvp.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum HttpResponse {

    SUCCESS(HttpStatus.OK, "success"),
    FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "failed")
    ;

    private final HttpStatus httpStatus;
    private final String msg;

    HttpResponse(HttpStatus httpStatus, String msg){
        this.httpStatus = httpStatus;
        this.msg = msg;
    }
}
