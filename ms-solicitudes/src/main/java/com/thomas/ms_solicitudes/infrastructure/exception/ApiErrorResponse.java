package com.thomas.ms_solicitudes.infrastructure.exception;

import java.time.OffsetDateTime;

public record ApiErrorResponse(
		OffsetDateTime timestamp,
		Integer status,
		String error,
		String message,
		String path
) {
}
