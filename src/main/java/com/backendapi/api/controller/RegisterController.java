package com.backendapi.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backendapi.api.dtos.reqdto.LogRequestDto;
import com.backendapi.api.dtos.reqdto.RegisterRequestDTO;
import com.backendapi.api.dtos.respdto.WrapLoginResultAndDto;
import com.backendapi.api.model.enums.LoginResult;
import com.backendapi.api.model.enums.RegisterResult;
import com.backendapi.api.service.RegisterService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class RegisterController {

    private RegisterService registerService;

    RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    ResponseEntity<String> registered(@Valid @RequestBody RegisterRequestDTO newUser) {
        RegisterResult r = registerService.createUser(newUser);
        if (r == RegisterResult.DUPLICATE_USER) {
            return new ResponseEntity<>("Already Username/gmail ragistered", HttpStatus.CONFLICT);
        } else if (r == RegisterResult.ERROR) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("user created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    ResponseEntity<?> logging(@RequestBody LogRequestDto logRequestDto) {
        WrapLoginResultAndDto lr = registerService.login(logRequestDto);
        if (lr.getLoginResult() == LoginResult.FAIL) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (lr.getLoginResult() == LoginResult.NOT_FOUND) {
            return new ResponseEntity<>("Please Register First", HttpStatus.BAD_REQUEST);
        } else if (lr.getLoginResult() == LoginResult.WRONG_PASSWORD) {
            return new ResponseEntity<>("please Provide correct password", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(lr.getLoginResponseDto(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/test")
    String test(Authentication authentication) {
        return "Hello " + authentication.getName();
    }

}
