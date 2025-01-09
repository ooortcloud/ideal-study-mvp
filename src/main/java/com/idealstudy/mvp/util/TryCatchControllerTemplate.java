package com.idealstudy.mvp.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

@Slf4j
public class TryCatchControllerTemplate {

    public static <T> ResponseEntity<T> execute(Callable<T> callable) {

        T result = null;
        try {
            result = callable.call();
            return ResponseEntity.ok(result);
        } catch (SecurityException e) {
            log.error(e + " : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatusCode.valueOf(403));
        } catch (IOException e) {
            log.error("입출력 오류: " + e + " : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatusCode.valueOf(500));
        } catch (RuntimeException e) {
            log.error(e + " : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatusCode.valueOf(500));
        } catch (Exception e) {
            log.error(e + " : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatusCode.valueOf(404));
        }
    }
}
