package com.thomas.ms_solicitudes.application.port.in;

import java.time.LocalDate;

import com.thomas.ms_solicitudes.application.result.MetricasResult;
import com.thomas.ms_solicitudes.application.result.SolicitudPageResult;
import com.thomas.ms_solicitudes.application.result.SolicitudResult;
import com.thomas.ms_solicitudes.domain.EstadoSolicitud;
import reactor.core.publisher.Mono;

public interface ConsultarSolicitudUseCase {
    Mono<SolicitudResult> consultarPorId(Long id);

    /**
     * Lista las solicitudes de un usuario autenticado.
     * La implementacion concreta debe resolver filtros y paginacion en application/infrastructure.
     */
    default Mono<SolicitudPageResult> listarSolicitudesUsuario(Long usuarioId, EstadoSolicitud estado, Integer page, Integer size) {
        return Mono.error(new UnsupportedOperationException("Listado de solicitudes de usuario aun no implementado"));
    }

    /**
     * Lista todas las solicitudes para administradores.
     * El controller solo delega; las reglas de autorizacion y filtros viven fuera de REST.
     */
    default Mono<SolicitudPageResult> listarTodasSolicitudes(EstadoSolicitud estado, LocalDate fechaDesde, LocalDate fechaHasta, Integer page, Integer size) {
        return Mono.error(new UnsupportedOperationException("Listado administrativo de solicitudes aun no implementado"));
    }

    /**
     * Obtiene metricas agregadas para administradores.
     * La agregacion real debe permanecer en application/persistence, no en el controller.
     */
    default Mono<MetricasResult> obtenerMetricas() {
        return Mono.error(new UnsupportedOperationException("Metricas de solicitudes aun no implementadas"));
    }
}
