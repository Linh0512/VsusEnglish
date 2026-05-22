package org.Linh0512.module.user.application.service;

import lombok.RequiredArgsConstructor;
import org.Linh0512.core.dto.response.PagedResponse;
import org.Linh0512.core.exception.ResourceAlreadyExistsException;
import org.Linh0512.core.exception.ResourceNotFoundException;
import org.Linh0512.core.security.jwt.JwtTokenProvider;
import org.Linh0512.module.user.domain.entity.Account;
import org.Linh0512.module.user.domain.entity.UserProfile;
import org.Linh0512.module.user.domain.repository.AccountRepository;
import org.Linh0512.module.user.domain.repository.UserProfileRepository;
import org.Linh0512.module.user.infrastructure.mapper.UserMapper;
import org.Linh0512.module.user.presentation.dto.request.user.ProfileInforRequest;
import org.Linh0512.module.user.presentation.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final AccountRepository accountRepository;
    private final UserProfileRepository userProfileRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;


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

    @Override
    public PagedResponse<UserResponse> getAllUsers(int pageNo, int pageSize) {
        if(pageNo < 0)
            throw new IllegalArgumentException("Page number cannot be negative");

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "joinDate"));

        Page<UserProfile> profilePage = userProfileRepository.findAll(pageable);

        //Mapstruct
        List<UserResponse> content = profilePage.getContent()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();

        // Anual Mapping
//        List<UserResponse> content = profilePage.getContent().stream()
//                .map(profile -> UserResponse.builder()
//                        .username(profile.getUsername())
//                        .fullname(profile.getFullname())
//                        .email(profile.getAccount().getEmail())
//                        .gender(profile.getGender().name())
//                        .country(profile.getCountry())
//                        .yob(profile.getYob())
//                        .avatarUrl(profile.getAvatarUrl())
//                        .bio(profile.getBio())
//                        .joinDate(profile.getJoinDate().toString())
//                        .ELO(profile.getElo())
//                        .contributionPoint(profile.getContributionPoint())
//                        .status(profile.getAccount().getStatus().name())
//                        .lastLogin(profile.getAccount().getLastLogin().toString())
//                        .build())
//                .toList();

        return new PagedResponse<>(
                content,
                profilePage.getNumber(),
                profilePage.getSize(),
                profilePage.getTotalPages(),
                profilePage.getTotalElements(),
                profilePage.isLast()
        );
    }
}
