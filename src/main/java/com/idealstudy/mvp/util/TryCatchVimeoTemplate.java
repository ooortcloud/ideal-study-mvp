package com.idealstudy.mvp.util;

import com.clickntap.vimeo.VimeoException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Callable;

@Slf4j
public class TryCatchVimeoTemplate {

    public static <T> T execute(Callable<T> fn) {
        try{
            return fn.call();
        } catch (VimeoException e) {
            log.error("vimeo 오류: " + e.getMessage());
        } catch (Exception e) {
            log.error(e + " : " + e.getMessage());
        }

        // if exception exists
        return null;
    }
}
