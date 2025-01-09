package com.idealstudy.mvp.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

@Aspect
@Component
@Slf4j(topic = "LogAspectJ")
public class LogAspectJ {

    @Before("execution(* com.idealstudy.mvp.presentation.controller..*.*(..))")
    public void printLog(JoinPoint joinPoint) {

        printControllerInfo(joinPoint);

        /// request 정보
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // Request Parameters 로그 출력
        printRequestParameter(request);

        // Request Body 로그 출력
        printRequestBody(request);
    }

    private void printControllerInfo(JoinPoint joinPoint) {
        String controllerName = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.info(controllerName + " " + methodName + "() 실행");
    }

    private void printRequestParameter(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        StringBuilder parameters = new StringBuilder("Request Parameters: ");
        boolean hasParameters = false;
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            parameters.append(paramName).append("=").append(request.getParameter(paramName));
            if (parameterNames.hasMoreElements()) {
                parameters.append(", ");
            }
            hasParameters = true;
        }
        if (hasParameters) {
            log.info(parameters.toString());
        }
    }

    private void printRequestBody(HttpServletRequest request) {
        StringBuilder body = new StringBuilder("Request Body: ");
        try {
            BufferedReader reader = request.getReader();
            String line;
            boolean hasBody = false;
            while ((line = reader.readLine()) != null) {
                body.append(line);
                hasBody = true;
            }
            if (hasBody) {
                log.info(body.toString());
            }
        } catch (IOException e) {
            log.error("Error reading request body", e);
        }
    }
}
