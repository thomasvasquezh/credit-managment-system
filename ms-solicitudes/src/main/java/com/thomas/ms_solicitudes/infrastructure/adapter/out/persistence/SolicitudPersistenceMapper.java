package com.thomas.ms_solicitudes.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.thomas.ms_solicitudes.domain.Solicitud;

@Component
public class SolicitudPersistenceMapper {
	public Solicitud toDomain(SolicitudEntity entity) {
		return new Solicitud(
				entity.getId(),
				entity.getUsuarioId(),
				entity.getMonto(),
				entity.getPlazoMeses(),
				entity.getProposito(),
				entity.getEstado(),
				entity.getMotivoRechazo(),
				entity.getFechaCreacion(),
				entity.getFechaActualizacion()
		);
	}

	public SolicitudEntity toEntity(Solicitud solicitud) {
		return SolicitudEntity.builder()
				.id(solicitud.getId())
				.usuarioId(solicitud.getUsuarioId())
				.monto(solicitud.getMonto())
				.plazoMeses(solicitud.getPlazoMeses())
				.proposito(solicitud.getProposito())
				.estado(solicitud.getEstado())
				.motivoRechazo(solicitud.getMotivoRechazo())
				.fechaCreacion(solicitud.getFechaCreacion())
				.fechaActualizacion(solicitud.getFechaActualizacion())
				.build();
	}
}
