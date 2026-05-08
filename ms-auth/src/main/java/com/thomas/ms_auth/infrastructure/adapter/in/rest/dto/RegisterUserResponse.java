package com.thomas.ms_auth.infrastructure.adapter.in.rest.dto;

import com.thomas.ms_auth.domain.UserRole;
import com.thomas.ms_auth.domain.UserStatus;

public record RegisterUserResponse (
        Long userId,
        String fullName,
        String email,
        UserRole role,
        UserStatus status
){
}
