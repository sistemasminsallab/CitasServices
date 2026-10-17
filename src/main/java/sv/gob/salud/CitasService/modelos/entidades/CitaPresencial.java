package sv.gob.salud.CitasService.modelos.entidades;

import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaPresencial extends Cita {
    private String consultorio;
    private Establecimiento establecimiento;
    private String indicaciones;

    public CitaPresencial(Long id, AgendaMedica agendaMedica, Expediente expediente, int orden, EstadoCita estadoCita, Prioridad prioridad, TipoCita tipo, Cita citaOrigen, LocalDate fechaCita, LocalTime horaInicio, LocalTime horaFin,  String consultorio, Establecimiento establecimiento, String indicaciones){
        super(id, agendaMedica, expediente, orden, estadoCita, prioridad, tipo, citaOrigen, fechaCita, horaInicio, horaFin);
        setConsultorio(consultorio);
        setEstablecimiento(establecimiento);
        setIndicaciones(indicaciones);
    }
    
    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public boolean tieneConsultorioAsignado() {
        return consultorio != null && !consultorio.isEmpty();
    }

    public boolean tieneEstablecimientoAsignado() {
        return establecimiento != null && !establecimiento.getNombre().isEmpty();
    }

    public String obtenerInformacionDeCitaPresencial() {
        if (tieneConsultorioAsignado() && tieneEstablecimientoAsignado()) {
            return "Establecimiento: " + establecimiento.getNombre() + ", Consultorio: " + consultorio;
        } else {
            return "No se ha asignado un establecimiento o consultorio para la cita presencial";
        }
    }

    @Override
    public Modalidad modalidad() {
        return Modalidad.PRESENCIAL;
    }

    @Override
    public String detalleLugar() {
        return obtenerInformacionDeCitaPresencial();
    }


}



