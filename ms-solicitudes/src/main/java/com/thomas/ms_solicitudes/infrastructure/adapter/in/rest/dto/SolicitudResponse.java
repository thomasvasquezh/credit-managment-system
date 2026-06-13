package com.thomas.ms_solicitudes.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.thomas.ms_solicitudes.domain.EstadoSolicitud;

public record SolicitudResponse(
		Long id,
		Long usuarioId,
		BigDecimal monto,
		Integer plazoMeses,
		String proposito,
		EstadoSolicitud estado,
		String motivoRechazo,
		OffsetDateTime fechaCreacion,
		OffsetDateTime fechaActualizacion
) {
}
