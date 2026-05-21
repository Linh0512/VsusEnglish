package org.Linh0512.module.user.domain.repository;

import org.Linh0512.module.user.domain.entity.Account;
import org.Linh0512.module.user.domain.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    Optional<UserProfile> findByAccount(Account account);

    Optional<UserProfile> findByUsername(String username);

    Optional<UserProfile> findByPhone(String phone);

    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
}
