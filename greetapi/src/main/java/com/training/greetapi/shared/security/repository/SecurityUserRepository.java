package com.training.greetapi.shared.security.repository;

import com.training.greetapi.shared.security.entity.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Long> {

    Optional<SecurityUser> findByUsername(String username);

}
