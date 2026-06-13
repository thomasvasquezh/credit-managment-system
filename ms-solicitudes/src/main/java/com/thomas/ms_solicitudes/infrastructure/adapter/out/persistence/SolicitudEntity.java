package com.thomas.ms_solicitudes.infrastructure.adapter.out.persistence;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.thomas.ms_solicitudes.domain.EstadoSolicitud;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("solicitudes")
public class SolicitudEntity {

	@Id
	private Long id;
	private Long usuarioId;
	private BigDecimal monto;
	private Integer plazoMeses;
	private String proposito;
	private EstadoSolicitud estado;
	private String motivoRechazo;
	private OffsetDateTime fechaCreacion;
	private OffsetDateTime fechaActualizacion;

}
