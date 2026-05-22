package org.Linh0512.module.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String username;
    private String fullname;
    private String gender;
    private String country;
    private LocalDate yob;
    private String email;
    private String avatarUrl;
    private String bio;
    private String joinDate;
    private String lastLogin;
    private int ELO;
    private int contributionPoint;
    private String status;
}
