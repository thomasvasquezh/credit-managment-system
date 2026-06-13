package com.thomas.ms_solicitudes.infrastructure.adapter.out.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SolicitudR2dbcRepository extends ReactiveCrudRepository<SolicitudEntity, Long> {
}
