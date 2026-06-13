package com.thomas.ms_solicitudes.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Solicitud {

	private Long id;
	private Long usuarioId;
	private BigDecimal monto;
	private Integer plazoMeses;
	private String proposito;
	private EstadoSolicitud estado;
	private String motivoRechazo;
	private OffsetDateTime fechaCreacion;
	private OffsetDateTime fechaActualizacion;


	public static Solicitud crear(
			Long usuarioId,
			BigDecimal monto,
			Integer plazoMeses,
			String proposito
		){
		OffsetDateTime ahora = OffsetDateTime.now();

		return Solicitud.builder()
				.usuarioId(usuarioId)
				.monto(monto)
				.plazoMeses(plazoMeses)
				.proposito(proposito)
				.estado(EstadoSolicitud.PENDIENTE)
				.motivoRechazo(null)
				.fechaCreacion(OffsetDateTime.now())
				.fechaActualizacion(OffsetDateTime.now())
				.build();
	}

}