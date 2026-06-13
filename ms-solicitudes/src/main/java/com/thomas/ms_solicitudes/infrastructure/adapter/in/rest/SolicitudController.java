package com.thomas.ms_solicitudes.infrastructure.adapter.in.rest;

import java.time.LocalDate;
import java.util.List;

import com.thomas.ms_solicitudes.application.command.CrearSolicitudCommand;
import com.thomas.ms_solicitudes.application.command.GestionarSolicitudCommand;
import com.thomas.ms_solicitudes.application.port.in.ConsultarSolicitudUseCase;
import com.thomas.ms_solicitudes.application.port.in.CrearSolicitudUseCase;
import com.thomas.ms_solicitudes.application.port.in.GestionarSolicitudUseCase;
import com.thomas.ms_solicitudes.application.result.MetricasResult;
import com.thomas.ms_solicitudes.application.result.SolicitudPageResult;
import com.thomas.ms_solicitudes.application.result.SolicitudResult;
import com.thomas.ms_solicitudes.domain.EstadoSolicitud;
import com.thomas.ms_solicitudes.infrastructure.adapter.in.rest.dto.CrearSolicitudRequest;
import com.thomas.ms_solicitudes.infrastructure.adapter.in.rest.dto.MetricasResponse;
import com.thomas.ms_solicitudes.infrastructure.adapter.in.rest.dto.RechazarSolicitudRequest;
import com.thomas.ms_solicitudes.infrastructure.adapter.in.rest.dto.SolicitudPageResponse;
import com.thomas.ms_solicitudes.infrastructure.adapter.in.rest.dto.SolicitudResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

	private final CrearSolicitudUseCase crearSolicitudUseCase;
	private final ConsultarSolicitudUseCase consultarSolicitudUseCase;
	private final GestionarSolicitudUseCase gestionarSolicitudUseCase;

	public SolicitudController(
			CrearSolicitudUseCase crearSolicitudUseCase,
			ConsultarSolicitudUseCase consultarSolicitudUseCase,
			GestionarSolicitudUseCase gestionarSolicitudUseCase
	) {
		this.crearSolicitudUseCase = crearSolicitudUseCase;
		this.consultarSolicitudUseCase = consultarSolicitudUseCase;
		this.gestionarSolicitudUseCase = gestionarSolicitudUseCase;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<SolicitudResponse> crearSolicitud(
			@RequestHeader("X-User-Id") Long usuarioId,
			@Valid @RequestBody Mono<CrearSolicitudRequest> request
	) {
		return request
				.map(dto -> new CrearSolicitudCommand(
						usuarioId,
						dto.monto(),
						dto.plazoMeses(),
						dto.proposito()
				))
				.flatMap(crearSolicitudUseCase::crearSolicitud)
				.map(this::toResponse);
	}

	@GetMapping
	public Mono<SolicitudPageResponse> listarMisSolicitudes(
			@RequestHeader("X-User-Id") Long usuarioId,
			@RequestParam(required = false) EstadoSolicitud estado,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size
	) {
		return consultarSolicitudUseCase
				.listarSolicitudesUsuario(usuarioId, estado, page, size)
				.map(this::toPageResponse);
	}

	@GetMapping("/{id}")
	public Mono<SolicitudResponse> consultarPorId(@PathVariable Long id) {
		return consultarSolicitudUseCase
				.consultarPorId(id)
				.map(this::toResponse);
	}

	@PutMapping("/{id}/gestionar")
	public Mono<SolicitudResponse> gestionarSolicitud(
			@PathVariable Long id,
			@RequestParam(defaultValue = "true") boolean aprobar,
			@Valid @RequestBody(required = false) RechazarSolicitudRequest request
	) {
		String motivo = request != null ? request.motivo() : null;
		GestionarSolicitudCommand command = new GestionarSolicitudCommand(id, aprobar, motivo);

		return gestionarSolicitudUseCase
				.gestionarSolicitud(command)
				.map(this::toResponse);
	}

	@GetMapping("/admin/todas")
	public Mono<SolicitudPageResponse> listarTodas(
			@RequestParam(required = false) EstadoSolicitud estado,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "20") Integer size
	) {
		return consultarSolicitudUseCase
				.listarTodasSolicitudes(estado, fechaDesde, fechaHasta, page, size)
				.map(this::toPageResponse);
	}

	@GetMapping("/admin/metricas")
	public Mono<MetricasResponse> obtenerMetricas() {
		return consultarSolicitudUseCase
				.obtenerMetricas()
				.map(this::toMetricasResponse);
	}

	private SolicitudResponse toResponse(SolicitudResult result) {
		return new SolicitudResponse(
				result.id(),
				result.usuarioId(),
				result.monto(),
				result.plazoMeses(),
				result.proposito(),
				result.estado(),
				result.motivoRechazo(),
				result.fechaCreacion(),
				result.fechaActualizacion()
		);
	}

	private SolicitudPageResponse toPageResponse(SolicitudPageResult result) {
		List<SolicitudResponse> content = result.content()
				.stream()
				.map(this::toResponse)
				.toList();

		return new SolicitudPageResponse(
				content,
				result.page(),
				result.size(),
				result.totalElements(),
				result.totalPages()
		);
	}

	private MetricasResponse toMetricasResponse(MetricasResult result) {
		return new MetricasResponse(
				result.totalPendientes(),
				result.totalEnRevision(),
				result.totalAprobadas(),
				result.totalRechazadas(),
				result.montoTotalAprobado()
		);
	}
}
