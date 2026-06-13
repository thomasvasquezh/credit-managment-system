package com.thomas.ms_solicitudes.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CrearSolicitudRequest(
		@NotNull
		@DecimalMin(value = "1000.0", inclusive = true)
		@DecimalMax(value = "500000.0", inclusive = true)
		BigDecimal monto,

		@NotNull
		@Min(6)
		@Max(60)
		Integer plazoMeses,

		@NotBlank
		@Size(min = 10, max = 500)
		String proposito
) {
}
