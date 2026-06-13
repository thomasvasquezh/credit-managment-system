package com.thomas.ms_solicitudes.infrastructure.config;

import java.time.Clock;

import com.thomas.ms_solicitudes.application.port.in.ConsultarSolicitudUseCase;
import com.thomas.ms_solicitudes.application.port.in.CrearSolicitudUseCase;
import com.thomas.ms_solicitudes.application.port.in.GestionarSolicitudUseCase;
import com.thomas.ms_solicitudes.application.port.out.SolicitudRepositoryPort;
import com.thomas.ms_solicitudes.application.service.ConsultarSolicitudService;
import com.thomas.ms_solicitudes.application.service.CrearSolicitudService;
import com.thomas.ms_solicitudes.application.service.GestionarSolicitudService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}

	@Bean
	public CrearSolicitudUseCase crearSolicitudUseCase(SolicitudRepositoryPort solicitudRepositoryPort) {
		return new CrearSolicitudService(solicitudRepositoryPort);
	}


	@Bean
	public ConsultarSolicitudUseCase consultarSolicitudUseCase(SolicitudRepositoryPort solicitudRepositoryPort) {
		return new ConsultarSolicitudService(solicitudRepositoryPort);
	}

	@Bean
	public GestionarSolicitudUseCase gestionarSolicitudUseCase(SolicitudRepositoryPort solicitudRepositoryPort) {
		return new GestionarSolicitudService(solicitudRepositoryPort);
	}
}
