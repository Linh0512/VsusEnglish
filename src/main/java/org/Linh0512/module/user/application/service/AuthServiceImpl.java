package org.Linh0512.module.user.application.service;

import lombok.RequiredArgsConstructor;
import org.Linh0512.core.security.jwt.JwtTokenProvider;
import org.Linh0512.module.user.domain.entity.Account;
import org.Linh0512.module.user.domain.entity.Role;
import org.Linh0512.module.user.domain.entity.TokenBlacklist;
import org.Linh0512.module.user.domain.repository.AccountRepository;
import org.Linh0512.module.user.domain.repository.RoleRepository;
import org.Linh0512.module.user.domain.repository.TokenBlacklistRepository;
import org.Linh0512.module.user.presentation.dto.request.auth.GrantAdminRequest;
import org.Linh0512.module.user.presentation.dto.request.auth.LoginRequest;
import org.Linh0512.module.user.presentation.dto.request.auth.RegisterRequest;
import org.Linh0512.module.user.presentation.dto.response.AuthResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final RoleRepository roleRepository;

    @Override
    public void register(RegisterRequest request) {
        if(accountRepository.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("Email already exists");

        Account account = Account.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .status(Account.Status.active)
                .lastLogin(LocalDateTime.now())
                .build();
        Role userRole = roleRepository.findByRoleName(Role.RoleName.USER.name())
                        .orElseThrow(() -> new RuntimeException("User role not found in database"));
        account.addRole(userRole);

        accountRepository.save(account);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), account.getPasswordHash()))
            throw new RuntimeException("Invalid email or password");

        if (account.getStatus() == Account.Status.banned)
            throw new RuntimeException("Account is banned");

        account.setStatus(Account.Status.active);
        account.setLastLogin(LocalDateTime.now());
        accountRepository.save(account);

        String token = jwtTokenProvider.generateToken(account.getEmail(), account.getRoles());

        return AuthResponse.builder()
                .token(token)
                .email(account.getEmail())
                .message("Login successfully")
                .build();
    }

    @Override
    public void logout(String token) {
        String email = jwtTokenProvider.getEmailFromToken(token);
        LocalDateTime tokenExpiry = LocalDateTime.ofInstant(
                jwtTokenProvider.getTokenExpiryDate(token).toInstant(),
                ZoneId.systemDefault()
        );

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setStatus(Account.Status.inactive);

        TokenBlacklist blacklist = TokenBlacklist.builder()
                .token(token)
                .email(email)
                .tokenExpiry(tokenExpiry)
                .build();
        tokenBlacklistRepository.save(blacklist);
    }

    @Override
    public void grantAdminRole(GrantAdminRequest request) {
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Role adminRole = roleRepository.findByRoleName(Role.RoleName.ADMIN.name())
                .orElseThrow(() -> new RuntimeException("Admin role not found in database"));

        if (account.getRoles().contains(adminRole))
            throw new RuntimeException("Account already has admin role");
        account.addRole(adminRole);
        accountRepository.save(account);
    }
}
