package com.thomas.ms_solicitudes.application.port.in;

import com.thomas.ms_solicitudes.application.command.GestionarSolicitudCommand;
import com.thomas.ms_solicitudes.application.result.SolicitudResult;
import reactor.core.publisher.Mono;

public interface GestionarSolicitudUseCase {
    Mono<SolicitudResult> gestionarSolicitud(GestionarSolicitudCommand gestionarSolicitudCommand);

}
