package sv.gob.salud.CitasService.modelos.fabricas;

import java.time.LocalDate;
import java.time.LocalTime;

import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.entidades.Establecimiento;
import sv.gob.salud.CitasService.modelos.entidades.Expediente;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;

public class DatosCita {
    private Long id;
    private AgendaMedica agendaMedica;
    private Expediente expediente;
    private int orden;
    private EstadoCita estadoCita;
    private Prioridad prioridad;
    private TipoCita tipo;
    private Cita citaOrigen;
    private LocalDate fechaCita;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    // Presencial
    private String consultorio;
    private Establecimiento establecimiento;
    private String indicaciones;

    // Virtual
    private String url;
    private String plataforma;

    // Domiciliaria
    private String direccionVisita;
    private String referenciaUbicacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AgendaMedica getAgendaMedica() {
        return agendaMedica;
    }

    public void setAgendaMedica(AgendaMedica agendaMedica) {
        this.agendaMedica = agendaMedica;
    }

    public Expediente getExpediente() {
        return expediente;
    }

    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public EstadoCita getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(EstadoCita estadoCita) {
        this.estadoCita = estadoCita;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public TipoCita getTipo() {
        return tipo;
    }

    public void setTipo(TipoCita tipo) {
        this.tipo = tipo;
    }

    public Cita getCitaOrigen() {
        return citaOrigen;
    }

    public void setCitaOrigen(Cita citaOrigen) {
        this.citaOrigen = citaOrigen;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
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

    public String getDireccionVisita() {
        return direccionVisita;
    }

    public void setDireccionVisita(String direccionVisita) {
        this.direccionVisita = direccionVisita;
    }

    public String getReferenciaUbicacion() {
        return referenciaUbicacion;
    }

    public void setReferenciaUbicacion(String referenciaUbicacion) {
        this.referenciaUbicacion = referenciaUbicacion;
    }
}
