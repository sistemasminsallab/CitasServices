package sv.gob.salud.CitasService.dto;

import sv.gob.salud.CitasService.modelos.entidades.Cita;

import java.time.LocalDate;
import java.time.LocalTime;

public record CitaRespuesta(Long id, Long agendaId, String modalidad, String estado, String prioridad,
                            LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, String lugar) {

    public static CitaRespuesta de(Cita cita) {
        return new CitaRespuesta(cita.getId(), cita.getAgendaMedica().getId(), cita.modalidad().name(),
                cita.getEstadoCita().name(), cita.getPrioridad().name(), cita.getFechaCita(),
                cita.getHoraInicio(), cita.getHoraFin(), cita.detalleLugar());
    }
}
