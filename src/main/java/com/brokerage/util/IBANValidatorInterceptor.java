package com.brokerage.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.regex.Pattern;

@Component
public class IBANValidatorInterceptor implements HandlerInterceptor {

    private static final Pattern IBAN_PATTERN = Pattern.compile("[A-Z]{2}\\d{2}[A-Z\\d]{1,30}");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String iban = request.getParameter("iban");
        if (iban != null && !isValidIBAN(iban)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid IBAN");
            return false;
        }
        return true;
    }

    private boolean isValidIBAN(String iban) {
        return IBAN_PATTERN.matcher(iban).matches();
    }
}
