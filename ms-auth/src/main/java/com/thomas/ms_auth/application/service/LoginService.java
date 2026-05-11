package com.thomas.ms_auth.application.service;

import com.thomas.ms_auth.application.command.LoginCommand;
import com.thomas.ms_auth.application.port.in.LoginUseCase;
import com.thomas.ms_auth.application.port.out.PasswordEncoderPort;
import com.thomas.ms_auth.application.port.out.TokenGeneratorPort;
import com.thomas.ms_auth.application.port.out.UserRepositoryPort;
import com.thomas.ms_auth.application.result.AuthResult;
import com.thomas.ms_auth.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginService implements LoginUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final TokenGeneratorPort tokenGeneratorPort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public AuthResult login(LoginCommand command) {
        validateCommand(command);

        String normalizedEmail = command.email().trim().toLowerCase();

        User user = userRepositoryPort.findByEmail(normalizedEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!user.isActive()) {
            throw new IllegalArgumentException("User is inactive");
        }

        if (!passwordEncoderPort.matches(command.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = tokenGeneratorPort.generateToken(user);
        return new AuthResult(
                user.getId(),
                user.fullName(),
                user.getEmail(),
                user.getRole(),
                "Bearer",
                token,
                tokenGeneratorPort.getExpirationInSeconds()
        );
    }
    private void validateCommand(LoginCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("Command is required");
        }

        if (command.email() == null || command.email().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (command.password() == null || command.password().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
    }
}
