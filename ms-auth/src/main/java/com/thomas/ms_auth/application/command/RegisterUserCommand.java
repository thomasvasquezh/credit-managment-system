package com.thomas.ms_auth.application.command;

public record RegisterUserCommand(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
