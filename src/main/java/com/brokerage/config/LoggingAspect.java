package com.brokerage.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.brokerage.controller.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Method called: {}", joinPoint.getSignature().getName());
    }

    @After("execution(* com.brokerage.controller.*.*(..))")
    public void logAfterMethod(JoinPoint joinPoint) {
        logger.info("Method execution finished: {}", joinPoint.getSignature().getName());
    }

    @Around("execution(* com.brokerage.controller.*.*(..))")
    public Object logAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before executing method: {}", joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        logger.info("After executing method: {}", joinPoint.getSignature().getName());
        return result;
    }
}
