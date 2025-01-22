package com.idealstudy.mvp.util;

import com.idealstudy.mvp.error.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

@Deprecated
@Slf4j
public class TryCatchControllerTemplate {


    public static <T> ResponseEntity<T> execute(Callable<T> callable) {

        T result = null;
        try {
            result = callable.call();
            return ResponseEntity.ok(result);
        } catch (CustomException e) {
            log.error(e + " : " + e.getMessage());
            /// custom 예외 클래스를 사용
            /// error message를 전달하기 위해서는 현재 모든 컨트롤러의 반환 타입을 wildcard를 사용하도록 변경해야 함...
            return new ResponseEntity<>(result, HttpStatusCode.valueOf(e.getExceptionInfo().getHttpStatusCode()));
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
