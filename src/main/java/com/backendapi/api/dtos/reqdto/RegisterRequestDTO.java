package com.backendapi.api.dtos.reqdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    @NotBlank(message = "UserName is required")
    private String userName;
    @NotBlank(message =  "Password is required")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
        message = "Password must contain atleast one upperCase ,lowerCase, number and special Character"
    )
    private String password;
    @NotBlank(message = "email is required")
    @Email(message = "Invalid email formate")
    private String email;
}
