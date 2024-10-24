package com.brokerage.config;

import com.brokerage.annotation.LocalizedMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Aspect
@Component
public class MessageAspect {

    @Autowired
    private MessageSource messageSource;

    @Around("@annotation(localizedMessage)")
    public Object getLocalizedMessage(ProceedingJoinPoint joinPoint, LocalizedMessage localizedMessage) throws Throwable {
        Locale locale = Locale.getDefault();
        String message = messageSource.getMessage(localizedMessage.key(), null, locale);
        return joinPoint.proceed(new Object[]{message});
    }
}
