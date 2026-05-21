package org.Linh0512.module.user.application.service;

import org.Linh0512.module.user.presentation.dto.request.user.ProfileInforRequest;

public interface UserService {
    void addProfile(String token, ProfileInforRequest request);
    void updateProfile(String token, ProfileInforRequest request);
    void deleteProfileByEmail(String token);
}
