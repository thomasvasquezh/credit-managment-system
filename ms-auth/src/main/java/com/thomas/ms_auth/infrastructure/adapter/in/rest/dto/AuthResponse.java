package com.thomas.ms_auth.infrastructure.adapter.in.rest.dto;

import com.thomas.ms_auth.domain.UserRole;

public record AuthResponse(
        Long userId,
        String fullName,
        String email,
        UserRole role,
        String tokenType,
        String accessToken,
        Long expiresIn

) {
}
