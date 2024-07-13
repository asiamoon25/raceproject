package com.rc.raceproject.config;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LoggingAspect {

    @Autowired
    private HttpServletRequest request;

    @Pointcut("execution(* com.rc.raceproject.controller..*(..)) && !@annotation(com.rc.raceproject.annotation.NoLogging)")
    protected void applicationControllerPackagePointcut(){}

    @Pointcut("execution(* com.rc.raceproject.service..*(..)) && !@annotation(com.rc.raceproject.annotation.NoLogging)")
    protected void applicationServicePackagePointcut(){}

    @Before("applicationControllerPackagePointcut()")
    public void beforeController(JoinPoint joinPoint) {
        logBefore(joinPoint, "controller");
    }

    @Before("applicationServicePackagePointcut()")
    public void beforeService(JoinPoint joinPoint) {
        logBefore(joinPoint, "service");
    }

    @AfterReturning(value = "applicationControllerPackagePointcut()", returning = "result")
    public void afterController(JoinPoint joinPoint, Object result) {
        logAfter(joinPoint, result, "controller");
    }

    @AfterReturning(value = "applicationServicePackagePointcut()", returning = "result")
    public void afterService(JoinPoint joinPoint, Object result) {
        logAfter(joinPoint, result, "service");
    }

    @AfterThrowing(value = "applicationControllerPackagePointcut()", throwing = "exception")
    public void afterThrowingController(JoinPoint joinPoint, Exception exception) {
        logAfter(joinPoint, exception, "controller");
    }

    @AfterThrowing(value = "applicationServicePackagePointcut()", throwing = "exception")
    public void afterThrowingService(JoinPoint joinPoint, Exception exception) {
        logAfter(joinPoint, exception, "service");
    }

    private void logBefore(JoinPoint joinPoint, String type) {
        String fullClassName = joinPoint.getSignature().getDeclaringTypeName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String clientIp = request.getRemoteAddr();

        // https header
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }

        // log method parameter
        Map<String, Object> params = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            params.put(paramName, request.getParameter(paramName));
        }

        String reqLogDir = "req/" + type + "/" + className + "/" + methodName;
        createDirectories(reqLogDir);
        ThreadContext.put("headers", !headers.isEmpty() ? headers.toString() : null);
        ThreadContext.put("beforeClassName", className);
        ThreadContext.put("beforeMethodName", methodName);
        ThreadContext.put("clientIp", clientIp);
        Logger logger = LogManager.getLogger(fullClassName);
        logger.info(params.toString());
    }

    private void logAfter(JoinPoint joinPoint, Object object, String type) {
        String fullClassName = joinPoint.getSignature().getDeclaringTypeName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Logger logger = LogManager.getLogger(fullClassName);

        String resLogDir = "res/" + type + "/" + className + "/" + methodName;
        createDirectories(resLogDir);
        ThreadContext.put("afterClassName", className);
        ThreadContext.put("afterMethodName", methodName);

        if(object instanceof Exception) {
            logger.info("exception: " + (Exception) object);
        } else {
            logger.info("result: " + object);
        }

        ThreadContext.clearAll();
    }

    private void createDirectories(String dirPath) {
        String rootPath = System.getProperty("LOG_DIR");
        String fullPath = rootPath + "/" + dirPath;
        File dir = new File(fullPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
