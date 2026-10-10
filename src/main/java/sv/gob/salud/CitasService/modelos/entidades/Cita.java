package sv.gob.salud.CitasService.modelos.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {
    private Long id;
    private AgendaMedica agendaMedica;
    private Expediente expediente;
    private int orden;
    private EstadoCita estadoCita;
    private Prioridad prioridad;
    private TipoCita tipoCita;
    private Cita CitaOrigen;
    
}
