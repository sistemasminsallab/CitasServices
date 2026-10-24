package sv.gob.salud.CitasService.modelos.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;

public class CitaVirtual extends Cita {
    private String url;
    private String plataforma;

    public CitaVirtual(Long id, AgendaMedica agendaMedica, Expediente expediente, int orden,
                       EstadoCita estadoCita, Prioridad prioridad, TipoCita tipo, Cita citaOrigen,
                       LocalDate fechaCita, LocalTime horaInicio, LocalTime horaFin,
                       String url, String plataforma) {
        super(id, agendaMedica, expediente, orden, estadoCita, prioridad, tipo, citaOrigen,
                fechaCita, horaInicio, horaFin);
        this.url = url;
        this.plataforma = plataforma;
    }

    public String getUrl() { return url; }
    public String getPlataforma() { return plataforma; }

    public boolean tieneUrlAsignada() {
        return url != null && !url.isEmpty();
    }

    public boolean tienePlataformaAsignada() {
        return plataforma != null && !plataforma.isEmpty();
    }

    public String enlaceDeCitaVirtual() {
        if (tieneUrlAsignada() && tienePlataformaAsignada()) {
            return plataforma + ": " + url;
        }
        return "No se ha asignado un enlace de cita virtual";
    }

    @Override
    public Modalidad modalidad() {
        return Modalidad.VIRTUAL;
    }

    @Override
    public String detalleLugar() {
        return enlaceDeCitaVirtual();
    }

    @Override 
    public CitaVirtual clonarCon(Long nuevoId, LocalDate fecha, LocalTime inicio, LocalTime fin){
        return new CitaVirtual.Builder()
        .id(nuevoId)
        .agendaMedica(getAgendaMedica())
        .expediente(getExpediente())
        .orden(getOrden())
        .estadoCita(getEstadoCita())
        .prioridad(getPrioridad())
        .tipo(getTipo())
        .citaOrigen(getCitaOrigen())
        .fechaCita(fecha)
        .horaInicio(inicio)
        .horaFin(fin)
        .url(getUrl())
        .plataforma(getPlataforma())
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
        private String url;
        private String plataforma;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder agendaMedica(AgendaMedica agendaMedica) { this.agendaMedica = agendaMedica; return this; }
        public Builder expediente(Expediente expediente) { this.expediente = expediente; return this; }
        public Builder orden(int orden) { this.orden = orden; return this; }
        public Builder estadoCita(EstadoCita estadoCita) { this.estadoCita = estadoCita; return this; }
        public Builder prioridad(Prioridad prioridad) { this.prioridad = prioridad; return this; }
        public Builder tipo(TipoCita tipo) { this.tipo = tipo; return this; }
        public Builder citaOrigen(Cita citaOrigen) { this.citaOrigen = citaOrigen; return this; }
        public Builder fechaCita(LocalDate fechaCita) { this.fechaCita = fechaCita; return this; }
        public Builder horaInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; return this; }
        public Builder horaFin(LocalTime horaFin) { this.horaFin = horaFin; return this; }
        public Builder url(String url) { this.url = url; return this; }
        public Builder plataforma(String plataforma) { this.plataforma = plataforma; return this; }

        public CitaVirtual build() {
            return new CitaVirtual(id, agendaMedica, expediente, orden, estadoCita, prioridad, tipo,
                    citaOrigen, fechaCita, horaInicio, horaFin, url, plataforma);
        }
    }
}
