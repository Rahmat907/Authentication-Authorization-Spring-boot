package com.backendapi.api.dtos.reqdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class UpdataUserDto {
        @NotBlank (message = "username is required")
        public String userName;
        @NotBlank (message = "email is required")
        @Email (message = "email formate is required")
        public String email;
}
