package org.Linh0512.module.user.application.service;

import lombok.RequiredArgsConstructor;
import org.Linh0512.core.exception.ResourceAlreadyExistsException;
import org.Linh0512.core.exception.ResourceNotFoundException;
import org.Linh0512.core.security.jwt.JwtTokenProvider;
import org.Linh0512.module.user.domain.entity.Account;
import org.Linh0512.module.user.domain.entity.UserProfile;
import org.Linh0512.module.user.domain.repository.AccountRepository;
import org.Linh0512.module.user.domain.repository.UserProfileRepository;
import org.Linh0512.module.user.presentation.dto.request.user.ProfileInforRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final AccountRepository accountRepository;
    private final UserProfileRepository userProfileRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void addProfile(String token, ProfileInforRequest request) {

        Account account = accountRepository.findByEmail(jwtTokenProvider.getEmailFromToken(token))
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if(userProfileRepository.existsByUsername(request.getUsername()))
        {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        if(userProfileRepository.existsByPhone(request.getPhone()))
        {
            throw new ResourceAlreadyExistsException("Phone number already exists");
        }

        UserProfile userProfile = UserProfile.builder()
                .account(account)
                .username(request.getUsername())
                .fullname(request.getFullname())
                .gender(UserProfile.Gender.valueOf(request.getGender()))
                .phone(request.getPhone())
                .country(request.getCountry())
                .yob(LocalDate.parse(request.getYob()))
                .avatarUrl(request.getAvatarUrl())
                .bio(request.getBio())
                .build();

        userProfileRepository.save(userProfile);
    }

    @Override
    public void updateProfile(String token, ProfileInforRequest request) {
        Account account = accountRepository.findByEmail(jwtTokenProvider.getEmailFromToken(token))
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        UserProfile userProfile = userProfileRepository.findByAccount(account)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));

        userProfile.setUsername(request.getUsername());
        userProfile.setFullname(request.getFullname());
        userProfile.setGender(UserProfile.Gender.valueOf(request.getGender()));
        userProfile.setPhone(request.getPhone());
        userProfile.setCountry(request.getCountry());
        userProfile.setYob(LocalDate.parse(request.getYob()));
        userProfile.setAvatarUrl(request.getAvatarUrl());
        userProfile.setBio(request.getBio());

        userProfileRepository.save(userProfile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteProfileByEmail(String token) {
        Account account = accountRepository.findByEmail(jwtTokenProvider.getEmailFromToken(token))
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        UserProfile userProfile = userProfileRepository.findByAccount(account)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));

        userProfileRepository.delete(userProfile);
    }
}
