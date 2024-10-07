package com.training.greetapi.shared.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CSRFTokenLoggerFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(CSRFTokenLoggerFilter.class);

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        logger.info("CSRF token: {}", csrfToken.getToken());
        CsrfToken csrfToken1 = (CsrfToken) request.getAttribute("_csrf_token");
        if(csrfToken1!=null)
        logger.info("_csrf_token: {}", csrfToken1.getToken());

        filterChain.doFilter(request, response);
    }
}

