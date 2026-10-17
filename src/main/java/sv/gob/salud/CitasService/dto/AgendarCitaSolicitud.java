package sv.gob.salud.CitasService.dto;

import java.time.LocalTime;

import sv.gob.salud.CitasService.modelos.enums.Modalidad;

public record AgendarCitaSolicitud(Long agendaId, Modalidad modalidad, LocalTime horaInicio, LocalTime horaFin) {
}
