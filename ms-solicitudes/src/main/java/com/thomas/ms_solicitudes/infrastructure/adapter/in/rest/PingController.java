package com.thomas.ms_solicitudes.infrastructure.adapter.in.rest;

import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class PingController {

	@GetMapping("/ping")
	public Mono<Map<String, Object>> ping() {
		return Mono.just(Map.of(
				"service", "ms-solicitudes",
				"status", "UP",
				"timestamp", OffsetDateTime.now()
		));
	}
}
