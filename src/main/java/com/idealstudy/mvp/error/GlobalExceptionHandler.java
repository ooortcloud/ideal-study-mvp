package com.idealstudy.mvp.error;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden  // solution of swagger bug
@RestControllerAdvice(
        basePackages = {"com/idealstudy/mvp/presentation/controller"}
)  // JSON
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionInfo> handleCustomException(CustomException e) {

        ExceptionInfo info = e.getExceptionInfo();
        log.error(e.getMessage());
        return ResponseEntity.status(info.getHttpStatusCode()).body(info);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {

        String msg = "예상하지 못한 예외 발생. 개발자에게 문의해주세요.";
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(msg);
    }
}
