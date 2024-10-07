package com.training.greetapi.shared.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthenticationEventAuditingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEventAuditingFilter.class);

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /*
         *
         * The OncePerRequestFilter only supports
         * HTTP filters. This is why the parameters
         * are directly given as HttpServletRequest
         * and HttpServletResponse.

         * */

        logger.info("AuthenticationEventAuditingFilter-->doFilter");
        filterChain.doFilter(request, response);
    }
}
