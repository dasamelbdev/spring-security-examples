package com.training.greetapi.shared.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationFailureEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureEntryPoint.class);

    public CustomAuthenticationFailureEntryPoint() {
        logger.info("creating CustomAuthenticationFailureEntryPoint");
    }

    /**
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        logger.info("at CustomAuthenticationFailureEntryPoint-commence");
        response.addHeader("auth-error", "wrong credentials");
        response.sendError(HttpStatus.UNAUTHORIZED.value());

    }
}
