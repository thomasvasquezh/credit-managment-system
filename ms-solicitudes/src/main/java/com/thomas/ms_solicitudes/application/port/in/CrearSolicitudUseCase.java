package com.thomas.ms_solicitudes.application.port.in;

import com.thomas.ms_solicitudes.application.command.CrearSolicitudCommand;
import com.thomas.ms_solicitudes.application.result.SolicitudResult;
import reactor.core.publisher.Mono;

public interface CrearSolicitudUseCase {
    Mono<SolicitudResult> crearSolicitud(CrearSolicitudCommand command);
}
