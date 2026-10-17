package sv.gob.salud.CitasService.modelos.entidades;

import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.modelos.excepciones.CitaNoDisponibleException;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public AgendaMedica(Long id, Empleado medico, Especialidad especialidad, LocalDate fecha,
                        RangoHorario rangoHorario, int cupoMaximo, boolean activa) {
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
    }

    public Long getId() { return id; }
    public Empleado getMedico() { return medico; }
    public Especialidad getEspecialidad() { return especialidad; }
    public LocalDate getFecha() { return fecha; }
    public RangoHorario getRangoHorario() { return rangoHorario; }
    public int getCupoMaximo() { return cupoMaximo; }
    public boolean isActiva() { return activa; }
    public Set<Cita> getCitas() { return Set.copyOf(citas); }


    public int cuposDisponibles() {
        return cupoMaximo - citas.size();
    }

    public CitaPresencial agendarPresencial(Long id, Expediente expediente, Prioridad prioridad,
                                            TipoCita tipo, LocalTime inicio, LocalTime fin, String consultorio, Establecimiento establecimiento) {
        CitaPresencial cita = new CitaPresencial(id, this, expediente, siguienteOrden(), EstadoCita.PROGRAMADA,
                prioridad, tipo, null, fecha, inicio, fin, consultorio, establecimiento, null);
        return registrar(cita);
    }

    public CitaVirtual agendarVirtual(Long id, Expediente expediente, Prioridad prioridad,
                                      TipoCita tipo, LocalTime inicio, LocalTime fin, String url, String plataforma) {
        CitaVirtual cita = new CitaVirtual(id, this, expediente, siguienteOrden(), EstadoCita.PROGRAMADA,
                prioridad, tipo, null, fecha, inicio, fin, url, plataforma);
        return registrar(cita);
    }

    private int siguienteOrden() {
        return citas.size() + 1;
    }

    /** Única puerta de entrada de citas: aquí viven todas las reglas de disponibilidad. */
    private <T extends Cita> T registrar(T cita) {
        if (!activa) {
            throw new CitaNoDisponibleException("La agenda no está activa");
        }
        if (cuposDisponibles() == 0) {
            throw new CitaNoDisponibleException("La agenda no tiene cupos disponibles");
        }
        if (!rangoHorario.contiene(cita.getHoraInicio()) || !rangoHorario.contiene(cita.getHoraFin())) {
            throw new CitaNoDisponibleException("La cita queda fuera del horario de la agenda");
        }
        if (citas.stream().anyMatch(cita::seSolapaCon)) {
            throw new CitaNoDisponibleException("La cita se solapa con otra ya agendada");
        }
        citas.add(cita);
        return cita;
    }
}
