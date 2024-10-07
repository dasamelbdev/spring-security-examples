package com.training.greetapi.shared.security.repository;

import com.training.greetapi.shared.security.entity.SecurityCsrfToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JPACSRFTokenRepository extends JpaRepository<SecurityCsrfToken, Long> {

    Optional<SecurityCsrfToken> findTokenByUserIdentifier(String userIdentifier);
}
