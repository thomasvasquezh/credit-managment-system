package com.thomas.ms_solicitudes.application.port.out;

import com.thomas.ms_solicitudes.domain.Solicitud;
import reactor.core.publisher.Mono;

public interface SolicitudRepositoryPort {
    Mono<Solicitud> save(Solicitud solicitud);
    Mono<Solicitud> findById(Long id);
}
