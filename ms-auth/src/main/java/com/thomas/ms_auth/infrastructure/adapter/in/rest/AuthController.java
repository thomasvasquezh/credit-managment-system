package com.thomas.ms_auth.infrastructure.adapter.in.rest;

import com.thomas.ms_auth.domain.UserRole;
import com.thomas.ms_auth.infrastructure.adapter.in.rest.dto.AuthResponse;
import com.thomas.ms_auth.infrastructure.adapter.in.rest.dto.LoginRequest;
import com.thomas.ms_auth.infrastructure.adapter.in.rest.dto.RegisterUserRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterUserRequest request) {
        return new AuthResponse(
                1L,
                request.firstName() + " " + request.lastName(),
                request.email(),
                UserRole.CLIENT,
                "Bearer",
                "mock-access-token",
                3600L
        );
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return new AuthResponse(
                1L,
                "Mock User",
                request.email(),
                UserRole.CLIENT,
                "Bearer",
                "mock-access-token",
                3600L
        );
    }   

}
