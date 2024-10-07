package com.training.greetapi.shared.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "security_users")
public class SecurityUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "account_non_expired", nullable = false)
    private Boolean accountNonExpired = false;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "credentials_non_expired", nullable = false)
    private Boolean credentialsNonExpired = false;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "account_non_locked", nullable = false)
    private Boolean accountNonLocked = false;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "security_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<SecurityRole> securityRoles = new LinkedHashSet<>();

}