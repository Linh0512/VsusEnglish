package org.Linh0512.module.user.application.service;

import org.Linh0512.module.user.presentation.dto.request.auth.GrantAdminRequest;
import org.Linh0512.module.user.presentation.dto.request.auth.LoginRequest;
import org.Linh0512.module.user.presentation.dto.request.auth.RegisterRequest;
import org.Linh0512.module.user.presentation.dto.response.AuthResponse;

public interface AuthService {
    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    void logout(String token);
    void grantAdminRole(GrantAdminRequest request);
}
