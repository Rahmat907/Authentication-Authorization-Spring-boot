package com.backendapi.api.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendapi.api.dtos.reqdto.LogRequestDto;
import com.backendapi.api.dtos.reqdto.RegisterRequestDTO;
import com.backendapi.api.dtos.respdto.WrapLoginResultAndDto;
import com.backendapi.api.model.enums.LoginResult;
import com.backendapi.api.model.enums.RegisterResult;
import com.backendapi.api.model.enums.Role;
import com.backendapi.api.service.RegisterService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/")
public class RegisterController {
    
    private RegisterService registerService;

    RegisterController(RegisterService registerService){
        this.registerService = registerService;
    }

    @PostMapping("/")
    ResponseEntity<String> registered(@Valid @RequestBody RegisterRequestDTO newUser){
        String userName = newUser.getUserName();
        String password = newUser.getPassword();
        String email = newUser.getEmail();
        if(userName == null || password == null || email == null || userName == "" || password == "" || email == ""){
            return new ResponseEntity<>("please provide username and password",HttpStatus.BAD_REQUEST);
        }
         RegisterResult r = registerService.createUser(newUser);
        if(r == RegisterResult.DUPLICATE_USER){
           return new ResponseEntity<>("Already ragistered User",HttpStatus.CONFLICT);
        }else if(r == RegisterResult.ERROR){
            return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }       
       return new ResponseEntity<>("user created successfully",HttpStatus.CREATED); 
    }

    @PostMapping("/login")
    ResponseEntity<?> logging(@RequestBody LogRequestDto logRequestDto){
        String email = logRequestDto.getEmail();
        String password = logRequestDto.getPassword();
        if(email == null || email == "" || password == null || password == ""){
            return new ResponseEntity<>("Please provide email and password", HttpStatus.BAD_REQUEST);
        }
        WrapLoginResultAndDto lr = registerService.login(logRequestDto);
        if(lr.getLoginResult() == LoginResult.FAIL){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }else if(lr.getLoginResult() == LoginResult.NOT_FOUND){
            return new ResponseEntity<>("Please Register First", HttpStatus.BAD_REQUEST);
        }else if(lr.getLoginResult() == LoginResult.WRONG_PASSWORD){
            return new ResponseEntity<>("please Provide correct password", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(lr.getLoginResponseDto(), HttpStatus.ACCEPTED);
    }

    
}
