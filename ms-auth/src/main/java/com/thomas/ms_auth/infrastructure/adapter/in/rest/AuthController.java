package com.thomas.ms_auth.infrastructure.adapter.in.rest;

import com.thomas.ms_auth.application.command.LoginCommand;
import com.thomas.ms_auth.application.command.RegisterUserCommand;
import com.thomas.ms_auth.application.port.in.LoginUseCase;
import com.thomas.ms_auth.application.port.in.RegisterUserUseCase;
import com.thomas.ms_auth.application.result.AuthResult;
import com.thomas.ms_auth.application.result.RegisterUserResult;
import com.thomas.ms_auth.infrastructure.adapter.in.rest.dto.AuthResponse;
import com.thomas.ms_auth.infrastructure.adapter.in.rest.dto.LoginRequest;
import com.thomas.ms_auth.infrastructure.adapter.in.rest.dto.RegisterUserRequest;
import com.thomas.ms_auth.infrastructure.adapter.in.rest.dto.RegisterUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;

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

        AuthResult result = loginUseCase.login(
                new LoginCommand(
                request.email(),
                request.password()
            ));
        return new AuthResponse(
                result.userId(),
                result.fullName(),
                result.email(),
                result.role(),
                result.tokenType(),
                result.accessToken(),
                result.expiresIn()
        );
    }

    //proff
    @GetMapping("/me")
    public String me(Authentication authentication) {
        return "Authenticated user: " + authentication.getName();
    }

}
