package com.thomas.ms_auth.application.result;

import com.thomas.ms_auth.domain.UserRole;

public record AuthResult(
        Long userId,
        String fullName,
        String email,
        UserRole role,
        String tokenType,
        String accessToken,
        Long expiresIn
) {

}
