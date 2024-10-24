package com.brokerage.config;

import com.brokerage.annotation.LocalizedMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MessageAspectTest {

    @Mock
    private MessageSource messageSource;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @InjectMocks
    private MessageAspect messageAspect;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLocalizedMessage() throws Throwable {
        LocalizedMessage localizedMessage = mock(LocalizedMessage.class);
        when(localizedMessage.key()).thenReturn("greeting");
        when(messageSource.getMessage("greeting", null, Locale.getDefault())).thenReturn("Hello");
        when(joinPoint.proceed(any())).thenReturn("Hello");

        Object result = messageAspect.getLocalizedMessage(joinPoint, localizedMessage);

        assertEquals("Hello", result);
        verify(messageSource).getMessage("greeting", null, Locale.getDefault());
        verify(joinPoint).proceed(new Object[]{"Hello"});
    }
}