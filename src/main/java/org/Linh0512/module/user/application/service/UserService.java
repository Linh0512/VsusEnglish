package org.Linh0512.module.user.application.service;

import org.Linh0512.core.dto.response.PagedResponse;
import org.Linh0512.module.user.presentation.dto.request.user.ProfileInforRequest;
import org.Linh0512.module.user.presentation.dto.response.UserResponse;

public interface UserService {
    void addProfile(String token, ProfileInforRequest request);
    void updateProfile(String token, ProfileInforRequest request);
    void deleteProfileByEmail(String token);

    PagedResponse<UserResponse> getAllUsers(int pageNo, int pageSize);
}
