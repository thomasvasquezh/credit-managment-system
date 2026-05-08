package com.thomas.ms_auth.application.result;

import com.thomas.ms_auth.domain.UserRole;
import com.thomas.ms_auth.domain.UserStatus;

public record RegisterUserResult(
        Long userId,
        String fullName,
        String email,
        UserRole role,
        UserStatus status
) {
}
