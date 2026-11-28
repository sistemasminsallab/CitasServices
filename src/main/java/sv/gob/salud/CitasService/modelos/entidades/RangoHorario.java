package sv.gob.salud.CitasService.modelos.entidades;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

import sv.gob.salud.CitasService.modelos.enums.Modalidad;

/** Objeto de valor: una jornada de atención. Inmutable, validada al crearse e igual por contenido. */
public record RangoHorario(Long id, LocalTime horaInicio, LocalTime horaFin, Modalidad modalidad) {

    /** Constructor compacto: valida antes de asignar los campos; no hay forma de crear una jornada inválida. */
    public RangoHorario {
        Objects.requireNonNull(horaInicio, "La hora de inicio no puede ser nula");
        Objects.requireNonNull(horaFin, "La hora de fin no puede ser nula");
        Objects.requireNonNull(modalidad, "La modalidad no puede ser nula");
        if (!horaInicio.isBefore(horaFin)) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin");
        }
    }

    public long duracionMinutos() {
        return Duration.between(horaInicio, horaFin).toMinutes();
    }

    public boolean contiene(LocalTime hora) {
        if (hora == null) return false;
        return !hora.isBefore(horaInicio) && !hora.isAfter(horaFin);
    }

    public boolean seSolapaCon(RangoHorario otro) {
        if (otro == null) return false;
        return horaInicio.isBefore(otro.horaFin) && otro.horaInicio.isBefore(horaFin);
    }
}
