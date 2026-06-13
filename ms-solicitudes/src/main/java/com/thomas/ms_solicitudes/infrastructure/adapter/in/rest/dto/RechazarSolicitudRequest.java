package com.thomas.ms_solicitudes.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RechazarSolicitudRequest(
		@NotBlank
		@Size(min = 10, max = 1000)
		String motivo
) {
}
