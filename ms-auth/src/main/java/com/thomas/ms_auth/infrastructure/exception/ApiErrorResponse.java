package com.thomas.ms_auth.infrastructure.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponse(
        String message,
        int status,
        String path,
        LocalDateTime timestamp,
        List<String> errors
) {
}
