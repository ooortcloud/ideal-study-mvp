package com.idealstudy.mvp.error;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionInfo> handleCustomException(CustomException e) {

        ExceptionInfo info = e.getExceptionInfo();
        return ResponseEntity.status(info.getHttpStatusCode()).body(info);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {

        String msg = "예상하지 못한 예외 발생. 개발자에게 문의해주세요.";
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(msg);
    }
}
