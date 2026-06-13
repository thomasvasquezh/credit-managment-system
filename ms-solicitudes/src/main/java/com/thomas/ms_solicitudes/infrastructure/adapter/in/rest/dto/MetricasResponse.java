package com.thomas.ms_solicitudes.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;

public record MetricasResponse(
		Integer totalPendientes,
		Integer totalEnRevision,
		Integer totalAprobadas,
		Integer totalRechazadas,
		BigDecimal montoTotalAprobado
) {
}
