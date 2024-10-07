package com.training.greetapi.shared.security.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        logger.info("Creating CustomAuthenticationProvider");
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("at CustomAuthenticationProvider-authenticate");
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
                logger.info("grantedAuthority :{}", grantedAuthority.getAuthority());
            }
            logger.info("at CustomAuthenticationProvider-authenticate-successful");
            return new UsernamePasswordAuthenticationToken(userName, password, userDetails.getAuthorities());
        } else throw new BadCredentialsException("Bad credentials");
    }


    @Override
    public boolean supports(Class<?> authentication) {
        logger.info("at CustomAuthenticationProvider-supports");
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
