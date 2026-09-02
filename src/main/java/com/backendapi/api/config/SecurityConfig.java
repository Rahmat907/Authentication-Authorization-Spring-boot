package com.backendapi.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity , JwtAuthFilter jwtAuthFilter){
        httpSecurity
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth ->auth
            .requestMatchers("/api/","/api/login")
            .permitAll()
            .anyRequest()
            .authenticated()
        )
        .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
