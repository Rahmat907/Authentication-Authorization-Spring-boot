package com.backendapi.api.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backendapi.api.model.UserModel;
import com.backendapi.api.repo.RegisterRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{
     private final RegisterRepo registerRepo;
    public CustomUserDetailsService(RegisterRepo registerRepo){
        this.registerRepo = registerRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserModel user = registerRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        
        return User.builder()
        .username(user.getEmail())
        .password(user.getPassword())
        .roles(user.getRole().name())
        .build();
    }
}
