package com.idealstudy.mvp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "LogAspectJ")
public class LogAspectJ {

    @Before("execution(* com.idealstudy.mvp.presentation.controller..*.*(..))")
    public void printControllerMethod(JoinPoint joinPoint) {

        String controllerName = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.info(controllerName + " " + methodName + "() 실행");
    }
}
