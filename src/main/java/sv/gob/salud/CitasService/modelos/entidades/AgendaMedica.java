package sv.gob.salud.CitasService.modelos.entidades;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class AgendaMedica {
    private Long id;
    private Empleado medico;
    private Especialidad especialidad;
    private LocalDate fecha;
    private RangoHorario rangoHorario;
    private int cupoMaximo;
    private boolean activa = true;
    private Set<Cita> citas = new HashSet<>();

    //Constructor
    public AgendaMedica(Long id, Empleado medico, Especialidad especialidad, LocalDate fecha, RangoHorario rangoHorario, int cupoMaximo, boolean activa) {
            if (cupoMaximo <= 0) {
                throw new IllegalArgumentException("El cupo máximo debe ser mayor que cero");
            }
                        
            this.id = id;
            this.medico = medico;
            this.especialidad = especialidad;
            this.fecha = fecha;
            this.rangoHorario = rangoHorario;
            this.cupoMaximo = cupoMaximo;
            this.activa = activa;
            this.citas = new HashSet<>();
    }

    //metodos
    public Set<RangoHorario> buscarCupo(LocalDate fecha, Empleado empleado, Especialidad especialidad){
        // 1. Inicializar el conjunto correctamente (usando HashSet)
        Set<RangoHorario> cuposDisponibles = new HashSet<>();
        
        // Validación inicial opcional
        if (fecha == null || empleado == null
                || especialidad == null) {
            return cuposDisponibles;
        }

        if (!activa || !this.fecha.equals(fecha) || !this.medico.equals(empleado) || !this.especialidad.equals(especialidad)) {
            return cuposDisponibles;
        }

        if (citas.size() < cupoMaximo) {
            cuposDisponibles.add(rangoHorario);
        }

        return cuposDisponibles;
    }

    public Set<Cita> obtenerAgendaMedica(Empleado empleado, LocalDate fechaInicio, LocalDate fechaFinal){

        Set<Cita> resultado = new HashSet<>();

        if (empleado == null || fechaInicio == null  || fechaFinal == null || fechaInicio.isAfter(fechaFinal)) {
            return resultado;
        }

        return resultado;
    }

    public boolean verificarDisponibilidadCupo(LocalDate fecha, RangoHorario rangoHorario, Empleado empleado){

        if (fecha == null || rangoHorario == null || empleado == null) {
            return false;
        }

        if (!activa || !this.fecha.equals(fecha) || !this.medico.equals(empleado) || !this.rangoHorario.equals(rangoHorario)) {
            return false;
        }

        return true;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empleado getMedico() {
        return medico;
    }

    public void setMedico(Empleado medico) {
        this.medico = medico;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public RangoHorario getRangoHorario() {
        return rangoHorario;
    }

    public void setRangoHorario(RangoHorario rangoHorario) {
        this.rangoHorario = rangoHorario;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        if (cupoMaximo <= 0) {
            throw new IllegalArgumentException(
                "El cupo máximo debe ser mayor que cero"
            );
        }

        this.cupoMaximo = cupoMaximo;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Set<Cita> getCitas() {
        return Set.copyOf(citas);
    }

    public void setCitas(Set<Cita> citas) {
        this.citas = citas == null
                ? new HashSet<>()
                : new HashSet<>(citas);
    }
}