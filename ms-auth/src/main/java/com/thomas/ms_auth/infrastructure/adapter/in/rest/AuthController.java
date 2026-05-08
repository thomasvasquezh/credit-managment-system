package com.thomas.ms_auth.infrastructure.adapter.in.rest;

import com.thomas.ms_auth.application.command.RegisterUserCommand;
import com.thomas.ms_auth.application.port.in.RegisterUserUseCase;
import com.thomas.ms_auth.application.result.RegisterUserResult;
import com.thomas.ms_auth.domain.UserRole;
import com.thomas.ms_auth.infrastructure.adapter.in.rest.dto.AuthResponse;
import com.thomas.ms_auth.infrastructure.adapter.in.rest.dto.LoginRequest;
import com.thomas.ms_auth.infrastructure.adapter.in.rest.dto.RegisterUserRequest;
import com.thomas.ms_auth.infrastructure.adapter.in.rest.dto.RegisterUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUserResponse register(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserResult result = registerUserUseCase.register(
                new RegisterUserCommand(
                        request.firstName(),
                        request.lastName(),
                        request.email(),
                        request.password()
                )
         );
            return new RegisterUserResponse(
                    result.userId(),
                    result.fullName(),
                    result.email(),
                    result.role(),
                    result.status()
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
