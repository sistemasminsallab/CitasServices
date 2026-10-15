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
}