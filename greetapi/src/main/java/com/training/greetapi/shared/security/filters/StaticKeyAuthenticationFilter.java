package com.training.greetapi.shared.security.filters;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
public class StaticKeyAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(StaticKeyAuthenticationFilter.class);
    @Value("${static.auth.key}")
    private String staticAuthorizationKey;

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header != null && header.equals(staticAuthorizationKey)) {
            logger.info("StaticKeyAuthenticationFilter->doFilter: " + "key :" + header);
            filterChain.doFilter(request, response);
        } else
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }
}