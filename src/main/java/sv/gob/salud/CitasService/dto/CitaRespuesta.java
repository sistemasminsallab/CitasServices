package sv.gob.salud.CitasService.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import sv.gob.salud.CitasService.modelos.entidades.Cita;

/** Lo que el API expone de una cita; el modelo de dominio no sale directo por HTTP. */
public record CitaRespuesta(Long id, Long agendaId, String modalidad, String estado, String prioridad,
                            LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, String lugar) {

    public static CitaRespuesta de(Cita cita) {
        return new CitaRespuesta(cita.getId(), cita.getAgendaMedica().getId(), cita.modalidad().name(),
                cita.getEstadoCita().name(), cita.getPrioridad().name(), cita.getFechaCita(),
                cita.getHoraInicio(), cita.getHoraFin(), cita.detalleLugar());
    }
}
