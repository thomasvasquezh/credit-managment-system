package com.thomas.ms_solicitudes.infrastructure.adapter.out.persistence;

import com.thomas.ms_solicitudes.domain.Solicitud;
import org.springframework.stereotype.Component;

import com.thomas.ms_solicitudes.application.port.out.SolicitudRepositoryPort;
import reactor.core.publisher.Mono;
@Component
public class SolicitudRepositoryAdapter implements SolicitudRepositoryPort {

	private final SolicitudR2dbcRepository repository;
	private final SolicitudPersistenceMapper mapper;
	public SolicitudRepositoryAdapter(SolicitudR2dbcRepository repository, SolicitudPersistenceMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public Mono<Solicitud> save(Solicitud solicitud) {
		return Mono.justOrEmpty(solicitud)
				.switchIfEmpty(Mono.error(new IllegalArgumentException("La solicitud no puede ser nula")))
				.map(mapper::toEntity)
				.flatMap(repository::save)
				.map(mapper::toDomain);
	}

	@Override
	public Mono<Solicitud> findById(Long id) {
		if (id == null || id <= 0) {
			return Mono.error(new IllegalArgumentException("El id de la solicitud debe ser mayor a cero"));
		}
		return repository.findById(id)
				.map(mapper::toDomain);
	}
}
