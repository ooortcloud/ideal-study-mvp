package com.idealstudy.mvp.util;

import java.time.LocalDateTime;

public class TimeUtil {

    public static void checkRightDuration(LocalDateTime start, LocalDateTime end)
        throws IllegalArgumentException{

        if(start.isAfter(end))
            throw new IllegalStateException("종료 날짜가 시작 날짜보다 빠를 수 없습니다.");
    }
}
