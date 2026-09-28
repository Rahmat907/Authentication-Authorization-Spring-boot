package com.backendapi.api.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendapi.api.config.JwtUtil;
import com.backendapi.api.dtos.reqdto.LogRequestDto;
import com.backendapi.api.dtos.reqdto.RegisterRequestDTO;
import com.backendapi.api.dtos.reqdto.UpdataUserDto;
import com.backendapi.api.dtos.respdto.AddressDto;
import com.backendapi.api.dtos.respdto.LoginResponseDto;
import com.backendapi.api.dtos.respdto.UserResponse;
import com.backendapi.api.dtos.respdto.WrapLoginResultAndDto;
import com.backendapi.api.model.UserModel;
import com.backendapi.api.model.enums.LoginResult;
import com.backendapi.api.model.enums.RegisterResult;
import com.backendapi.api.repo.UserRepo;

@Service
public class UserService {
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    // this is constructor injection
    UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public RegisterResult createUser(RegisterRequestDTO userDto) {
        if (userRepo.existsByEmail(userDto.getEmail()) || userRepo.existsByUserName(userDto.getUserName()))
            return RegisterResult.DUPLICATE_USER;
        UserModel newUser = new UserModel();
        newUser.setUserName(userDto.getUserName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // newUser.setAddress();
        try {
            userRepo.save(newUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return RegisterResult.ERROR;
        }
        return RegisterResult.SUCCESS;
    }

    public WrapLoginResultAndDto login(LogRequestDto logRequestDto) {
        try {
            Optional<UserModel> ou = userRepo.findByEmail(logRequestDto.getEmail());
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

    public List<UserResponse> fetchAllUser(){
        return userRepo.findAll().stream().map(this::maptoUserRespose).collect(Collectors.toList());
    }

    public Optional<UserResponse> fetchUser(Long id){
        return userRepo.findById(id).map(this:: maptoUserRespose);
    }
    
    public boolean updateUser(Long id ,UpdataUserDto updataUserDto){
        return userRepo.findById(id).
        map(existingUser -> {
            existingUser.setEmail(updataUserDto.getEmail());
            existingUser.setUserName(updataUserDto.getUserName());
            userRepo.save(existingUser);
            return true;
        }).orElse(false);
    }

    public UserResponse maptoUserRespose(UserModel userModel){
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(userModel.getId()));
        response.setUserName(userModel.getUserName());
        response.setEmail(userModel.getEmail());
        response.setRole(userModel.getRole());
        if(userModel.getAddress() != null){
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(userModel.getAddress().getStreet());
            addressDto.setCity(userModel.getAddress().getCity());
            addressDto.setCountry(userModel.getAddress().getCountry());
            addressDto.setNotes(userModel.getAddress().getNotes());
            addressDto.setPhoneno(userModel.getAddress().getPhoneno());
            addressDto.setPincode(userModel.getAddress().getPincode());
        response.setAddress(addressDto);
        }
        return response;
    }
}
