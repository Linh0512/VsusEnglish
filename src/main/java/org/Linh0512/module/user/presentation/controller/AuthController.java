package org.Linh0512.module.user.presentation.controller;


import lombok.RequiredArgsConstructor;
import org.Linh0512.module.user.application.service.AuthService;
import org.Linh0512.module.user.presentation.dto.request.auth.GrantAdminRequest;
import org.Linh0512.module.user.presentation.dto.request.auth.LoginRequest;
import org.Linh0512.module.user.presentation.dto.request.auth.RegisterRequest;
import org.Linh0512.module.user.presentation.dto.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request)
    {
        authService.register(request);
        return ResponseEntity.ok("Register successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader)
    {   String token = authHeader.replace("Bearer ", "");
        authService.logout(token);
        return ResponseEntity.ok("Logout successfully");
    }

    @PostMapping("/grant-admin")
    public ResponseEntity<String> grantAdmin(@RequestBody GrantAdminRequest request)
    {
        authService.grantAdminRole(request);
        return ResponseEntity.ok("Grant admin role successfully");
    }
}

