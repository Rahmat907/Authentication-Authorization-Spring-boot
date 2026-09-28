package com.backendapi.api.dtos.respdto;

import com.backendapi.api.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class UserResponse {
    private String id;
    private String userName;
    private String email;
    private Role role;
    private AddressDto address;
}
