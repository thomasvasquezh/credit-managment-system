package com.thomas.ms_solicitudes.application.service;

import com.thomas.ms_solicitudes.application.port.in.ConsultarSolicitudUseCase;
import com.thomas.ms_solicitudes.application.port.out.SolicitudRepositoryPort;
import com.thomas.ms_solicitudes.application.result.SolicitudResult;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ConsultarSolicitudService implements ConsultarSolicitudUseCase {
    private final SolicitudRepositoryPort solicitudRepositoryPort;


    @Override
    public Mono<SolicitudResult> consultarPorId(Long id) {
        //debo validar si existe una solicitud creada primero
        if(id == null|| id<=0){
            return Mono.error(new IllegalArgumentException("El id ingresado no es valido"));
        }
        return solicitudRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("No se encontró una solicitud con el id: " + id)))
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
