package sv.gob.salud.CitasService.modelos.entidades;
import java.time.LocalDate;
import java.time.LocalTime;

import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;

public abstract class Cita {

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

    public Cita(Long id, 
            AgendaMedica agendaMedica,
            Expediente expediente,
            int orden,
            EstadoCita estadoCita,
            Prioridad prioridad,
            TipoCita tipo,
            Cita citaOrigen,
            LocalDate fechaCita,
            LocalTime horaInicio,
            LocalTime horaFin) {

        if (id == null || id <= 0) { 
            throw new IllegalArgumentException( "El ID debe ser mayor que cero"); 
        }

        if (orden <= 0) { 
            throw new IllegalArgumentException( "El orden debe ser mayor que cero"); 
        }

        this.id = id;
        this.agendaMedica = agendaMedica;
        this.expediente = expediente;
        this.orden = orden;
        this.estadoCita = estadoCita;
        this.prioridad = prioridad;
        this.tipo = tipo;
        this.citaOrigen = citaOrigen;
        this.fechaCita = fechaCita;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters 
    public Long getId(){ 
            return id; 
        } 
    
    public AgendaMedica getAgendaMedica(){ 
            return agendaMedica; 
        } 
        
    public Expediente getExpediente(){ 
        return expediente; 
    } 
    
    public int getOrden(){ 
        return orden; 
    } 
    
    public EstadoCita getEstadoCita(){ 
        return estadoCita; 
    } 
    
    public Prioridad getPrioridad(){ 
        return prioridad; 
    } 
    
    public TipoCita getTipo(){ 
        return tipo; 
    }  
    
    public Cita getCitaOrigen(){ 
        return citaOrigen;
     } 
     
     public LocalDate getFechaCita(){ 
        return fechaCita; 
    } 
    
    public LocalTime getHoraInicio(){ 
        return horaInicio; 
    } 
    
    public LocalTime getHoraFin(){ 
        return horaFin; 
    }

    //metodos
    public void cambiarEstadoCita(EstadoCita estadoCita){
        if (estadoCita == null) { 
            throw new IllegalArgumentException( "El estado de la cita no puede ser nulo"); 
        } 
        
        this.estadoCita = estadoCita;
    }

    public void reprogramarCita(LocalDate fechaCita, LocalTime horaInicio, LocalTime horaFin ){

        if (estaCancelada()) { 
            throw new IllegalStateException( "No se puede reprogramar una cita cancelada"); 
        }

        validarHorario(fechaCita, horaInicio, horaFin);

        this.fechaCita = fechaCita;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Validar fecha y horario 
    private void validarHorario( LocalDate fecha, LocalTime inicio, LocalTime fin) { 
        if (fecha == null || inicio == null || fin == null) { 
            throw new IllegalArgumentException( "La fecha y las horas son obligatorias"); 
        } 
        
        if (!inicio.isBefore(fin)) { 
            throw new IllegalArgumentException( "La hora de inicio debe ser anterior a la hora final"); } 
    }

    public boolean seSolapaCon(Cita otra){
         if(otra == null || estaCancelada() || otra.estaCancelada() || !fechaCita.equals(otra.fechaCita)){
             return false;
         }

         return horaInicio.isBefore(otra.horaFin) && otra.horaInicio.isBefore(horaFin);
    }

    public boolean estaCancelada() {
    return estadoCita == EstadoCita.CANCELADA;
    }

    public abstract Modalidad modalidad();

    public abstract String detalleLugar();
}