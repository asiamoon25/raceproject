package com.rc.raceproject.config;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LoggingAspect {

    @Autowired
    private HttpServletRequest request;

    @Pointcut("within(@org.springframework.stereotype.Controller *) && !@annotation(com.rc.raceproject.annotation.NoLogging)")
    public void controller(){}

    @Pointcut("execution(* com.rc.raceproject.controller..*(..)) && !@annotation(com.rc.raceproject.annotation.NoLogging) || execution(* com.rc.raceproject.service..*(..)) && !@annotation(com.rc.raceproject.annotation.NoLogging)")
    protected void applicationPackagePointcut(){}

    @Before("applicationPackagePointcut()")
    public void before(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String clientIp = request.getRemoteAddr();

        // https header
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }

        // log method parameter
        Map<String, Object> params = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            params.put(paramName, request.getParameter(paramName));
        }
        ThreadContext.put("headers", !headers.isEmpty() ? headers.toString() : null);
        ThreadContext.put("beforeClassName",className);
        ThreadContext.put("beforeMethodName", methodName);
        ThreadContext.put("clientIp", clientIp);
        Logger logger = LogManager.getLogger(className);
        logger.info(params.toString());
    }

    @AfterReturning(value = "applicationPackagePointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Logger logger = LogManager.getLogger(className);

        ThreadContext.put("afterClassName", className);
        ThreadContext.put("afterMethodName", methodName);

        logger.info("result: " + result);

        ThreadContext.clearAll();
    }

    @AfterThrowing(value = "applicationPackagePointcut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Logger logger = LogManager.getLogger(className);

        ThreadContext.put("afterClassName", className);
        ThreadContext.put("afterMethodName", methodName);

        logger.info("exception: " + exception);

        ThreadContext.clearAll();
    }


}
