package com.idealstudy.mvp.domain;

import com.idealstudy.mvp.enums.error.SystemErrorMsg;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class Attendance {

    /**
     * Map에는 다음의 값들이 존재한다.
     * - startDate: 입력된 년월의 1일
     * - endDate: 입력된 년월의 말일
     * @param year
     * @param month
     * @return
     */
    public Map<String, LocalDate> getDate(int year, int month) throws IllegalArgumentException{

        if(month < 1 || month > 12)
           throw new IllegalArgumentException(SystemErrorMsg.ILLEGAL_ARGUMENT_EXCEPTION.toString());

        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        if (year == now.getYear() && month == now.getMonthValue()) {
            endDate = now;
        }

        Map<String, LocalDate> map = new HashMap<>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return map;
    }

    @Deprecated
    public Map<String, LocalDate> getDate(LocalDate time){

        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate startDate = LocalDate.of(time.getYear(), time.getMonthValue(), 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        if (time.getYear() == now.getYear() && time.getMonthValue() == now.getMonthValue()) {
            endDate = now;
        }

        Map<String, LocalDate> map = new HashMap<>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return map;
    }
}
