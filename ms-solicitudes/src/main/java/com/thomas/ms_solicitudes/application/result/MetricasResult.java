package com.thomas.ms_solicitudes.application.result;

import java.math.BigDecimal;

public record MetricasResult(
		Integer totalPendientes,
		Integer totalEnRevision,
		Integer totalAprobadas,
		Integer totalRechazadas,
		BigDecimal montoTotalAprobado
) {
}
