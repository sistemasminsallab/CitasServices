package sv.gob.salud.CitasService.modelos.fabricas;

import java.time.LocalDate;
import java.time.LocalTime;

import sv.gob.salud.CitasService.modelos.entidades.*;
import sv.gob.salud.CitasService.modelos.enums.*;

/**
 * Datos de entrada para crear una Cita. Los campos específicos de cada modalidad
 * son opcionales: usa los que apliquen (consultorio para presencial, url para virtual...).
 * En M6 esto se refina con DTOs por modalidad; aquí basta un portador único.
 */
public record DatosCita(
        Long id, AgendaMedica agendaMedica, Expediente expediente, int orden,
        EstadoCita estadoCita, Prioridad prioridad, TipoCita tipo, Cita citaOrigen,
        LocalDate fechaCita, LocalTime horaInicio, LocalTime horaFin,
        String consultorio, Establecimiento establecimiento, String indicaciones,
        String direccionVisita, String referenciaUbicacion,
        String url, String plataforma) {
}
