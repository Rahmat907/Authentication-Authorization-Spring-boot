package com.backendapi.api.dtos.reqdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogRequestDto {
    @NotBlank(message = "email required")
    @Email(message = "Invalid email formate")
    private String email;
    @NotBlank(message = "password reqired")
    private String password;

}
