package org.Linh0512.module.user.presentation.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileInforRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Fullname is required")
    private String fullname;
    @NotBlank(message = "Gender is required")
    private String gender;
    @NotBlank(message = "Phone is required")
    private String phone;
    @NotBlank(message = "Country is required")
    private String country;
    @NotBlank(message = "YOB is required")
    private String yob;
    private String bio;
    private String avatarUrl;
}
