package com.backendapi.api.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendapi.api.config.JwtUtil;
import com.backendapi.api.dtos.reqdto.LogRequestDto;
import com.backendapi.api.dtos.reqdto.RegisterRequestDTO;
import com.backendapi.api.dtos.respdto.LoginResponseDto;
import com.backendapi.api.dtos.respdto.WrapLoginResultAndDto;
import com.backendapi.api.model.UserModel;
import com.backendapi.api.model.enums.LoginResult;
import com.backendapi.api.model.enums.RegisterResult;
import com.backendapi.api.model.enums.Role;
import com.backendapi.api.repo.RegisterRepo;

@Service
public class RegisterService {
    private RegisterRepo registerRepo;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    // this is constructor injection
    RegisterService(RegisterRepo registerRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.registerRepo = registerRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public RegisterResult createUser(RegisterRequestDTO userDto) {
        if (registerRepo.existsByEmail(userDto.getEmail()) || registerRepo.existsByUserName(userDto.getUserName()))
            return RegisterResult.DUPLICATE_USER;
        UserModel newUser = new UserModel();
        newUser.setUserName(userDto.getUserName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(Role.USER);
        try {
            registerRepo.save(newUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return RegisterResult.ERROR;
        }
        return RegisterResult.SUCCESS;
    }

    public WrapLoginResultAndDto login(LogRequestDto logRequestDto) {
        try {
            Optional<UserModel> ou = registerRepo.findByEmail(logRequestDto.getEmail());
            if (ou.isEmpty())
                return new WrapLoginResultAndDto(LoginResult.NOT_FOUND, null);
            UserModel um = ou.get();

            if (passwordEncoder.matches(logRequestDto.getPassword(), um.getPassword())) {
                String token = jwtUtil.generateToken(um.getEmail(), um.getId(), um.getRole());
                LoginResponseDto loginResponseDto = new LoginResponseDto(um.getId(), um.getEmail(), um.getUserName(),
                        token, um.getRole());
                return new WrapLoginResultAndDto(LoginResult.SUCCESS, loginResponseDto);
            } else {
                return new WrapLoginResultAndDto(LoginResult.WRONG_PASSWORD, null);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new WrapLoginResultAndDto(LoginResult.FAIL, null);
        }
    }
}
