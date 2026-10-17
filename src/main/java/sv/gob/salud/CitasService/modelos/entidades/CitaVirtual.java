package sv.gob.salud.CitasService.modelos.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;

public class CitaVirtual extends Cita {
    private String url;
    private String plataforma;

    public CitaVirtual(Long id, AgendaMedica agendaMedica, Expediente expediente, int orden,
                       EstadoCita estadoCita, Prioridad prioridad, TipoCita tipo, Cita citaOrigen,
                       LocalDate fechaCita, LocalTime horaInicio, LocalTime horaFin,
                       String url, String plataforma) {
        super(id, agendaMedica, expediente, orden, estadoCita, prioridad, tipo, citaOrigen,
                fechaCita, horaInicio, horaFin);
        this.url = url;
        this.plataforma = plataforma;
    }

    public String getUrl() { return url; }
    public String getPlataforma() { return plataforma; }

    public boolean tieneUrlAsignada() {
        return url != null && !url.isEmpty();
    }

    public boolean tienePlataformaAsignada() {
        return plataforma != null && !plataforma.isEmpty();
    }

    public String enlaceDeCitaVirtual() {
        if (tieneUrlAsignada() && tienePlataformaAsignada()) {
            return plataforma + ": " + url;
        }
        return "No se ha asignado un enlace de cita virtual";
    }

    @Override
    public Modalidad modalidad() {
        return Modalidad.VIRTUAL;
    }

    @Override
    public String detalleLugar() {
        return enlaceDeCitaVirtual();
    }
}