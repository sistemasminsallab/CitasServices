package sv.gob.salud.CitasService.modelos.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;

public class CitaVirtual extends Cita {
    // private Long id;
    private String url;
    private String plataforma;

    public CitaVirtual(Long id, AgendaMedica agendaMedica, Expediente expediente, int orden,
        EstadoCita estadoCita, Prioridad prioridad, TipoCita tipoCita, Cita citaOrigen,
        LocalDate fechaCita, LocalTime horaInicio, LocalTime horaFin, String url, String plataforma
    ) {
        super(id, agendaMedica, expediente, orden, estadoCita, prioridad, tipoCita, citaOrigen, fechaCita, horaInicio, horaFin);

        this.url = url;
        this.plataforma = plataforma;
    }

    public String getUrl() {
        return url;
    }

    public String getPlataforma() {
        return plataforma;
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
    
    @Override
    public Modalidad modalidad() {
        return Modalidad.VIRTUAL;
    }

    @Override
    public String detalleLugar() {
        return enlaceDeCitaVirtual();
    }
}
