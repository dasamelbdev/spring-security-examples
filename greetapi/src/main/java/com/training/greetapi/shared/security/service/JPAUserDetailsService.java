package com.training.greetapi.shared.security.service;

import com.training.greetapi.shared.security.dto.UserDetailsDTO;
import com.training.greetapi.shared.security.entity.SecurityUser;
import com.training.greetapi.shared.security.repository.SecurityUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JPAUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(JPAUserDetailsService.class);

    private final SecurityUserRepository securityUserRepository;

    public JPAUserDetailsService(SecurityUserRepository securityUserRepository) {
        this.securityUserRepository = securityUserRepository;
    }

    /**
     * @param username the username identifying the user whose data is required.
     * @return UserDetails implementation
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("JPAUserDetailsService-->loadUserByUsername");
        SecurityUser securityUser = securityUserRepository.findByUsername(username).orElseThrow();
        return new UserDetailsDTO(securityUser);
    }
}
