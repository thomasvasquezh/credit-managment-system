package com.thomas.ms_solicitudes.application.service;

import com.thomas.ms_solicitudes.application.command.CrearSolicitudCommand;
import com.thomas.ms_solicitudes.application.port.in.CrearSolicitudUseCase;
import com.thomas.ms_solicitudes.application.port.out.SolicitudRepositoryPort;
import com.thomas.ms_solicitudes.application.result.SolicitudResult;
import com.thomas.ms_solicitudes.domain.Solicitud;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CrearSolicitudService implements CrearSolicitudUseCase {
    private final SolicitudRepositoryPort solicitudRepository;

    @Override
    public Mono<SolicitudResult> crearSolicitud(CrearSolicitudCommand command) {
        return validateCommand(command)
                .then(Mono.fromSupplier(() ->
                        Solicitud.crear(
                                command.usuarioId(),
                                command.monto(),
                                command.plazoMeses(),
                                command.proposito()
                        )
                ))
                .flatMap(solicitudRepository::save)
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

    private Mono<Void> validateCommand(CrearSolicitudCommand command) {
        if (command == null) {
            return Mono.error(new IllegalArgumentException("El comando no puede ser nulo"));
        }
        if (command.monto() == null || command.monto().compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.error(new IllegalArgumentException("El monto debe ser mayor a cero"));
        }
        if (command.plazoMeses() == null || command.plazoMeses() <= 0) {
            return Mono.error(new IllegalArgumentException("El plazo en meses debe ser mayor a cero"));
        }
        return Mono.empty();
    }
}
