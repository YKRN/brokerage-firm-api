package com.brokerage.config;

import com.brokerage.interceptor.IBANValidatorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final IBANValidatorInterceptor ibanValidatorInterceptor;

    @Autowired
    public WebConfig(IBANValidatorInterceptor ibanValidatorInterceptor) {
        this.ibanValidatorInterceptor = ibanValidatorInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ibanValidatorInterceptor).addPathPatterns("/api/customer/withdraw");
    }
}
