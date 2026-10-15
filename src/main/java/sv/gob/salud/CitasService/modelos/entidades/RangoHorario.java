package sv.gob.salud.CitasService.modelos.entidades;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;

public class RangoHorario {
    private Long id;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Modalidad modalidad;

    // * Constructores
    public RangoHorario() {}
 
    public RangoHorario(Long id, LocalTime horaInicio, LocalTime horaFin, Modalidad modalidad) {
        this.id = id;
        this.horaInicio = Objects.requireNonNull(horaInicio, "La hora de inicio no puede ser nula");
        this.horaFin = Objects.requireNonNull(horaFin, "La hora de fin no puede ser nula");
        this.modalidad = Objects.requireNonNull(modalidad, "La modalidad no puede ser nula");
    }
 
    // * Getters
    public Long getId() { return id; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public Modalidad getModalidad() { return modalidad; }
    
    // * Setters
    public void setId(Long id) { this.id = id; }
    
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = Objects.requireNonNull(horaInicio, "La hora de inicio no puede ser nula");
    }
 
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = Objects.requireNonNull(horaFin, "La hora de fin no puede ser nula");
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = Objects.requireNonNull(modalidad, "La modalidad no puede ser nula");
    }
 
    // * Métodos de comportamiento
    public long getDuracionMinutos() {
        if (horaInicio == null || horaFin == null) return 0;
        return Duration.between(horaInicio, horaFin).toMinutes();
    }
 
    public boolean esValido() {
        if (horaInicio == null || horaFin == null) return false;
        return horaInicio.isBefore(horaFin);
    }
 
    public boolean respetaHoraMinimaDia(LocalTime horaMinimaDia) {
        if (horaInicio == null || horaMinimaDia == null) return false;
        return !horaInicio.isBefore(horaMinimaDia);
    }
 
    public boolean respetaJornadaMaxima(LocalTime horaMaximaDia) {
        if (horaFin == null || horaMaximaDia == null) return false;
        return !horaFin.isAfter(horaMaximaDia);
    }
 
    public boolean contiene(LocalTime hora) {
        if (hora == null || !esValido()) return false;
        return !hora.isBefore(horaInicio) && !hora.isAfter(horaFin);
    }
 
    public boolean seSolapaCon(RangoHorario otro) {
        if (otro == null || !this.esValido() || !otro.esValido()) return false;
        return this.horaInicio.isBefore(otro.getHoraFin()) && otro.getHoraInicio().isBefore(this.horaFin);
    }
}