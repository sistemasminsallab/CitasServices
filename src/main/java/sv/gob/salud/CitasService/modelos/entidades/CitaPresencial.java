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
        return establecimiento != null;
    }

    public String obtenerInformacionDeCitaPresencial() {
        if (tieneConsultorioAsignado() && tieneEstablecimientoAsignado()) {
            return "Establecimiento: " + establecimiento.getNombre() + ", Consultorio: " + consultorio;
        }
        return "No se ha asignado un establecimiento o consultorio para la cita presencial";
    }

    @Override
    public Modalidad modalidad() {
        return Modalidad.PRESENCIAL;
    }

    @Override
    public String detalleLugar() {
        return obtenerInformacionDeCitaPresencial();
    }

    @Override
    public CitaPresencial clonarCon(Long nuevoId, LocalDate fecha, LocalTime inicio, LocalTime fin) {
        return new CitaPresencial.Builder()
                .id(nuevoId)
                .agendaMedica(getAgendaMedica())
                .expediente(getExpediente())
                .orden(getOrden())
                .estadoCita(EstadoCita.PROGRAMADA)
                .prioridad(getPrioridad())
                .tipo(getTipo())
                .citaOrigen(this)              // enlaza el original
                .fechaCita(fecha)
                .horaInicio(inicio)
                .horaFin(fin)
                .consultorio(this.getConsultorio())
                .establecimiento(this.getEstablecimiento())
                .indicaciones(this.getIndicaciones())
                .build();
    }

     public static class Builder {
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
        private String consultorio;
        private Establecimiento establecimiento;
        private String indicaciones;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder agendaMedica(AgendaMedica a) { this.agendaMedica = a; return this; }
        public Builder expediente(Expediente e) { this.expediente = e; return this; }
        public Builder orden(int orden) { this.orden = orden; return this; }
        public Builder estadoCita(EstadoCita e) { this.estadoCita = e; return this; }
        public Builder prioridad(Prioridad p) { this.prioridad = p; return this; }
        public Builder tipo(TipoCita t) { this.tipo = t; return this; }
        public Builder citaOrigen(Cita c) { this.citaOrigen = c; return this; }
        public Builder fechaCita(LocalDate f) { this.fechaCita = f; return this; }
        public Builder horaInicio(LocalTime h) { this.horaInicio = h; return this; }
        public Builder horaFin(LocalTime h) { this.horaFin = h; return this; }
        public Builder consultorio(String c) { this.consultorio = c; return this; }
        public Builder establecimiento(Establecimiento e) { this.establecimiento = e; return this; }
        public Builder indicaciones(String i) { this.indicaciones = i; return this; }

        // La validación vive en el constructor: el Builder solo lo invoca (fail-fast)
        public CitaPresencial build() {
            return new CitaPresencial(id, agendaMedica, expediente, orden, estadoCita,
                    prioridad, tipo, citaOrigen, fechaCita, horaInicio, horaFin,
                    consultorio, establecimiento, indicaciones);
        }
    }


}
