package com.training.greetapi.shared.security.dto;

import com.training.greetapi.shared.security.entity.SecurityUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsDTO implements UserDetails {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsDTO.class);


    private final SecurityUser securityUser;

    public UserDetailsDTO(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        logger.info("UserDetailsDTO --->getAuthorities");
        return this.securityUser.getSecurityRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return this.securityUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.securityUser.getUsername();
    }


    @Override
    public boolean isAccountNonExpired() {
        return this.securityUser.getAccountNonExpired();
    }


    @Override
    public boolean isAccountNonLocked() {
        return this.securityUser.getAccountNonLocked();
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return this.securityUser.getCredentialsNonExpired();
    }


    @Override
    public boolean isEnabled() {
        return this.securityUser.getEnabled();
    }
}
