package com.thomas.ms_solicitudes.application.command;

public record GestionarSolicitudCommand(
		Long solicitudId,
		boolean aprobar,
		String motivo
) {
}
