package com.backendapi.api.dtos.respdto;

import com.backendapi.api.model.enums.LoginResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WrapLoginResultAndDto {
    private LoginResult loginResult;
    private LoginResponseDto loginResponseDto;
}
