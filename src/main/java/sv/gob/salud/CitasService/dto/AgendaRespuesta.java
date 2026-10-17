package sv.gob.salud.CitasService.dto;

import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;

import java.time.LocalDate;

public record AgendaRespuesta(Long id, String medico, LocalDate fecha, int cupoMaximo, int cuposDisponibles) {

    public static AgendaRespuesta de(AgendaMedica agenda) {
        return new AgendaRespuesta(agenda.getId(), agenda.getMedico().getNombreCompleto(), agenda.getFecha(),
                agenda.getCupoMaximo(), agenda.cuposDisponibles());
    }
}