package com.backendapi.api.dtos.respdto;

import com.backendapi.api.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
        private Long userId;
        private String email;
        private String userName;
        private String token;
        private Role role;
}
