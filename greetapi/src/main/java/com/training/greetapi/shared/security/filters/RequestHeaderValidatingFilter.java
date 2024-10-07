package com.training.greetapi.shared.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestHeaderValidatingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeaderValidatingFilter.class);

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logger.info("RequestHeaderValidatingFilter->doFilter");
        //we are checking each request for having a header called req-id
        String header = ((HttpServletRequest) request).getHeader("req-id");
        if (header == null || header.isBlank() || header.matches("^[a-zA-Z]+$")) {
            logger.info("RequestHeaderValidatingFilter->doFilter--> invalid header value for req-id header");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        filterChain.doFilter(request, response);


    }

}
