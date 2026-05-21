package org.Linh0512.module.user.domain.repository;

import org.Linh0512.module.user.domain.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, String> {
    Optional<TokenBlacklist> findByToken(String token);
    boolean existsByToken(String token);
}
