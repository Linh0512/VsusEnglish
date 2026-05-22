package org.Linh0512.module.user.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.Linh0512.module.user.application.service.UserService;
import org.Linh0512.module.user.presentation.dto.request.user.ProfileInforRequest;
import org.Linh0512.core.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @PostMapping("/add-profile")
    public ResponseEntity<ApiResponse<?>> addProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ProfileInforRequest request) {

        String token = authHeader.replace("Bearer ", "");
        userService.addProfile(token, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(null,"Add profile successfully"));
    }

    @PutMapping("/update-profile")
    public ResponseEntity<ApiResponse<?>> updateProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ProfileInforRequest request) {

        String token = authHeader.replace("Bearer ", "");
        userService.updateProfile(token, request);
        return ResponseEntity.ok(ApiResponse.success(null,"Update profile successfully"));
    }

    @DeleteMapping("/delete-profile")
    public ResponseEntity<ApiResponse<?>> deleteProfile(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        userService.deleteProfileByEmail(token);
        return ResponseEntity.ok(ApiResponse.success(null,"Delete profile successfully"));
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<ApiResponse<?>> getAllUsers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(
                ApiResponse.success(userService.getAllUsers(pageNo, pageSize),
                        "Get all users successfully"));
    }

}
