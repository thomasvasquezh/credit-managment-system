package com.thomas.ms_solicitudes.application.result;

import java.util.List;

/**
 * Resultado de aplicacion para representar una pagina de solicitudes.
 * Se mantiene en application para no hacer que los use cases dependan de DTOs REST.
 */
public record SolicitudPageResult(
		List<SolicitudResult> content,
		Integer page,
		Integer size,
		Long totalElements,
		Integer totalPages
) {
}
