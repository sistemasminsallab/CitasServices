package sv.gob.salud.CitasService.modelos.fabricas;

import java.time.LocalDate;
import java.time.LocalTime;

import sv.gob.salud.CitasService.modelos.entidades.*;
import sv.gob.salud.CitasService.modelos.enums.*; 

public record DatosCita(Long id, AgendaMedica agendaMedica, Expediente expediente, int orden,
        EstadoCita estadoCita, Prioridad prioridad, TipoCita tipo, Cita citaOrigen,
        LocalDate fechaCita, LocalTime horaInicio, LocalTime horaFin,
        String consultorio, Establecimiento establecimiento, String indicaciones,
        String direccionVisita, String referenciaUbicacion,
        String url, String plataforma) {
  
}
