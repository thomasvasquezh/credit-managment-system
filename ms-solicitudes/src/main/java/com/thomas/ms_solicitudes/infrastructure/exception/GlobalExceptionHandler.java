package com.thomas.ms_solicitudes.infrastructure.exception;

import java.time.Clock;
import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private final Clock clock;

	public GlobalExceptionHandler(Clock clock) {
		this.clock = clock;
	}
	@ExceptionHandler(IllegalArgumentException.class)
	public Mono<ResponseEntity<ApiErrorResponse>> handleIllegalArgument(
			IllegalArgumentException exception,
			ServerWebExchange exchange
	) {
		return buildErrorResponse(
				HttpStatus.BAD_REQUEST,
				exception.getMessage(),
				exchange
		);
	}


	@ExceptionHandler(Exception.class)
	public Mono<ResponseEntity<ApiErrorResponse>> handleGenericException(
			Exception exception,
			ServerWebExchange exchange
	) {
		return buildErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR,
				"Ocurrio un error interno en ms-solicitudes",
				exchange
		);
	}
	private Mono<ResponseEntity<ApiErrorResponse>> buildErrorResponse(
			HttpStatus status,
			String message,
			ServerWebExchange exchange
	) {
		ApiErrorResponse response = new ApiErrorResponse(
				OffsetDateTime.now(clock),
				status.value(),
				status.name(),
				message,
				exchange.getRequest().getPath().value()
		);

		return Mono.just(ResponseEntity.status(status).body(response));
	}
}
