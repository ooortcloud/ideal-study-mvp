package com.idealstudy.mvp.util;

import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

@Slf4j
public class TryCatchServiceTemplate {

    public static <T> T execute(Callable<T> callable, Runnable runnable, DBErrorMsg errorMsg) {

        try {
            if(runnable != null)
                runnable.run();

            return callable.call();
        } catch (SecurityException e) {
            log.error(e.getMessage());
            throw new SecurityException(e);
        } catch (IOException e) {
            log.error("입출력 오류: " + e + " : " + e.getMessage());
            throw new RuntimeException(errorMsg.toString());
        } catch (Exception e) {
            log.error(e.getMessage() + " : " + errorMsg.toString());
            e.printStackTrace();
            throw new RuntimeException(errorMsg.toString());
        }
    }
}
