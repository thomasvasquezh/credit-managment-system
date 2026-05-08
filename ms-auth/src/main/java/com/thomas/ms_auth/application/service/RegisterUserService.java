package com.thomas.ms_auth.application.service;

import com.thomas.ms_auth.application.command.RegisterUserCommand;
import com.thomas.ms_auth.application.port.in.RegisterUserUseCase;
import com.thomas.ms_auth.application.port.out.PasswordEncoderPort;
import com.thomas.ms_auth.application.port.out.UserRepositoryPort;
import com.thomas.ms_auth.application.result.RegisterUserResult;
import com.thomas.ms_auth.domain.User;
import com.thomas.ms_auth.domain.UserRole;
import com.thomas.ms_auth.domain.UserStatus;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public RegisterUserResult register(RegisterUserCommand command) {
        validateCommand(command);

        String normalizedEmail = command.email().trim().toLowerCase();

        if (userRepositoryPort.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.create(
                command.firstName(),
                command.lastName(),
                normalizedEmail,
                passwordEncoderPort.encode(command.password()),
                UserRole.CLIENT,
                UserStatus.ACTIVE,
                LocalDateTime.now()
        );

        User savedUser = userRepositoryPort.save(user);

        return new RegisterUserResult(
                savedUser.getId(),
                savedUser.fullName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getStatus()
        );
    }

    private void validateCommand(RegisterUserCommand command) {
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
