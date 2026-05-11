package com.thomas.ms_auth.application.command;

public record LoginCommand(
        String email,
        String password
) {

}
