package com.thomas.ms_solicitudes.application.result;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.thomas.ms_solicitudes.domain.EstadoSolicitud;

public record SolicitudResult(
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
