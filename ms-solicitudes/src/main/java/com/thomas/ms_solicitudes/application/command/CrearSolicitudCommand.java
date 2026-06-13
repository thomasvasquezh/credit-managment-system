package com.thomas.ms_solicitudes.application.command;

import java.math.BigDecimal;

public record CrearSolicitudCommand(
		Long usuarioId,
		BigDecimal monto,
		Integer plazoMeses,
		String proposito
) {
}
