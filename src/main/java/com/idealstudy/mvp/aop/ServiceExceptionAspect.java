package com.idealstudy.mvp.aop;

import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.error.CustomException;
import com.idealstudy.mvp.error.ExceptionInfo;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Aspect
@Component
@Log4j2
public class ServiceExceptionAspect {

    @Around("execution(* com.idealstudy.mvp.application.service..*(..))")
    public Object handleServiceException(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (CustomException e) {
            ExceptionInfo info = e.getExceptionInfo();
            log.error(info.getMessage());
            throw e;
        } catch (NoSuchElementException e) {
            log.error(DBErrorMsg.SELECT_ERROR.getMsg());
            throw new CustomException(DBErrorMsg.SELECT_ERROR);
        } catch (Throwable e) {
            log.error("예상하지 못한 오류 발생");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
