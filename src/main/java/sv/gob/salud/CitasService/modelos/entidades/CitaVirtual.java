package sv.gob.salud.CitasService.modelos.entidades;

import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaVirtual extends Cita {
    private String url;
    private String plataforma;

    public CitaVirtual(Long id, AgendaMedica agendaMedica, Expediente expediente, int orden, EstadoCita estadoCita, Prioridad prioridad, TipoCita tipo, Cita citaOrigen, LocalDate fechaCita, LocalTime horaInicio, LocalTime horaFin, String url, String plataforma) {
        super(id, agendaMedica, expediente, orden, estadoCita, prioridad, tipo, citaOrigen, fechaCita, horaInicio, horaFin);
        setUrl(url);
        setPlataforma(plataforma);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public boolean tieneUrlAsignada() {
        if (url == null || url.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean tienePlataformaAsignada() {
        if (plataforma == null || plataforma.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public String enlaceDeCitaVirtual() {
        if (tieneUrlAsignada() && tienePlataformaAsignada()) {
            return plataforma + ": " + url;
        } else {
            return "No se ha asignado un enlace de cita virtual";
        }
    }
}
