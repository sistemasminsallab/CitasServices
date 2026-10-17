package sv.gob.salud.CitasService.modelos.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;

public class CitaTelefonica extends Cita {
    private String numeroTelefono;

    public CitaTelefonica(Long id, AgendaMedica agendaMedica, Expediente expediente, int orden,
                       EstadoCita estadoCita, Prioridad prioridad, TipoCita tipo, Cita citaOrigen,
                       LocalDate fechaCita, LocalTime horaInicio, LocalTime horaFin,
                       String numeroTelefonoPaciente) {
        super(id, agendaMedica, expediente, orden, estadoCita, prioridad, tipo, citaOrigen, fechaCita, horaInicio, horaFin);
        this.numeroTelefono = numeroTelefonoPaciente;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String obtenerInformacionDeCitaTelefonica() {
        if (numeroTelefono != null && !numeroTelefono.isEmpty()) {
            return "Número de teléfono: " + numeroTelefono;
        } else {
            return "No se ha asignado un número de teléfono para la cita telefónica";
        }
    }


     @Override
    public Modalidad modalidad() {
        return Modalidad.TELEFONICA;
    }

    @Override
    public String detalleLugar() {
        return obtenerInformacionDeCitaTelefonica();
    }

    
    
}
