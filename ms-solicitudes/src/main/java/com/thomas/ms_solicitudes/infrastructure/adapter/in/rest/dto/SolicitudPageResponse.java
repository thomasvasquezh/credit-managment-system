package com.thomas.ms_solicitudes.infrastructure.adapter.in.rest.dto;

import java.util.List;

public record SolicitudPageResponse(
		List<SolicitudResponse> content,
		Integer page,
		Integer size,
		Long totalElements,
		Integer totalPages
) {
}
