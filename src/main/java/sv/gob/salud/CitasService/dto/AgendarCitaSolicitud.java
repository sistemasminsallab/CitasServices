package sv.gob.salud.CitasService.dto;

import sv.gob.salud.CitasService.modelos.enums.Modalidad;

import java.time.LocalTime;

public record AgendarCitaSolicitud(Long agendaId, Modalidad modalidad, LocalTime horaInicio, LocalTime horaFin) {
}