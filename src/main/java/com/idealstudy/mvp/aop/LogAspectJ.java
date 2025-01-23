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

        // request url 로그 출력
        printUrl(request);
        
        // Request Parameters 로그 출력
        printRequestParameter(request);
    }

    private void printControllerInfo(JoinPoint joinPoint) {
        String controllerName = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.info(controllerName + " " + methodName + "() 실행");
    }

    private void printUrl(HttpServletRequest request) {

        String requestUrl = request.getRequestURL().toString();
        log.info("Request URL: " + requestUrl);
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

    /**
     * HttpServletRequest에서 InputStream 또는 BufferedReader를 한 번 읽으면, 스트림이 소모되어 더 이상 읽을 수 없게 됩니다.
     * 이로 인해 컨트롤러에서 요청 본문을 다시 읽으려고 할 때 오류가 발생합니다.
     * @param request
     */
    @Deprecated
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
