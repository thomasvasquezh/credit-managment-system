package com.thomas.ms_solicitudes.application.service;

import com.thomas.ms_solicitudes.application.command.GestionarSolicitudCommand;
import com.thomas.ms_solicitudes.application.port.in.GestionarSolicitudUseCase;
import com.thomas.ms_solicitudes.application.port.out.SolicitudRepositoryPort;
import com.thomas.ms_solicitudes.application.result.SolicitudResult;
import com.thomas.ms_solicitudes.domain.EstadoSolicitud;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@RequiredArgsConstructor
public class GestionarSolicitudService implements GestionarSolicitudUseCase {
    private final SolicitudRepositoryPort solicitudRepositoryPort;

    @Override
    public Mono<SolicitudResult> gestionarSolicitud(GestionarSolicitudCommand gestionarSolicitudCommand) {
        if (gestionarSolicitudCommand == null || gestionarSolicitudCommand.solicitudId() == null || gestionarSolicitudCommand.solicitudId() <= 0) {
            return Mono.error(new IllegalArgumentException("ID de solicitud inválido"));
        }
        return solicitudRepositoryPort.findById(gestionarSolicitudCommand.solicitudId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Solicitud no encontrada")))
                .flatMap(s -> {
                    if (gestionarSolicitudCommand.aprobar()) {
                        s.setEstado(EstadoSolicitud.APROBADA);
                        s.setMotivoRechazo(null);
                    } else {
                        s.setEstado(EstadoSolicitud.RECHAZADA);
                        s.setMotivoRechazo(gestionarSolicitudCommand.motivo());
                    }
                    s.setFechaActualizacion(OffsetDateTime.now());
                    return solicitudRepositoryPort.save(s);
                })
                .map(s -> new SolicitudResult(
                        s.getId(),
                        s.getUsuarioId(),
                        s.getMonto(),
                        s.getPlazoMeses(),
                        s.getProposito(),
                        s.getEstado(),
                        s.getMotivoRechazo(),
                        s.getFechaCreacion(),
                        s.getFechaActualizacion()
                ));
    }
}
