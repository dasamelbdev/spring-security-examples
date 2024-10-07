package com.training.greetapi.shared.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "security_csrf_token")
public class SecurityCsrfToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 56)
    @NotNull
    @Column(name = "user_identifier", nullable = false, length = 56)
    private String userIdentifier;

    @NotNull
    @Lob
    @Column(name = "token", nullable = false)
    private String token;

}