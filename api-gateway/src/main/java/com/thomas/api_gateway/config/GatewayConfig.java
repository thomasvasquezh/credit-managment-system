package com.thomas.api_gateway.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class GatewayConfig {
    // se llama cuando el circuit breaker abre el circuito
    @GetMapping("/solicitudes")
    public Mono<ResponseEntity<Map<String, String>>> solicitudesFallback() {
        return Mono.just(
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(Map.of(
                                "error", "Servicio de solicitudes temporalmente no disponible",
                                "message", "Por favor intente nuevamente en unos minutos"
                        ))
        );
    }
}
