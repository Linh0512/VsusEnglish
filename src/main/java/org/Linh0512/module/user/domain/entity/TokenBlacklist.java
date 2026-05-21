package org.Linh0512.module.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "token_blacklist")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TokenBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String tokenId;

    @Column(name = "token",columnDefinition = "LONGTEXT", nullable = false)
    private String token;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "token_expiry", nullable = false)
    private LocalDateTime tokenExpiry;
}
