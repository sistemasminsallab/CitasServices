
package sv.gob.salud.CitasService.modelos.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.modelos.validacion.Validaciones;

public class CitaDomiciliaria extends Cita {

    private String direccionVisita;
    private String referenciaUbicacion;

    public CitaDomiciliaria(
            Long id,
            AgendaMedica agendaMedica,
            Expediente expediente,
            int orden,
            EstadoCita estadoCita,
            Prioridad prioridad,
            TipoCita tipo,
            Cita citaOrigen,
            LocalDate fechaCita,
            LocalTime horaInicio,
            LocalTime horaFin,
            String direccionVisita,
            String referenciaUbicacion) {

        super(
            id,
            agendaMedica,
            expediente,
            orden,
            estadoCita,
            prioridad,
            tipo,
            citaOrigen,
            fechaCita,
            horaInicio,
            horaFin
        );

        this.direccionVisita = validarDireccion(direccionVisita);
        this.referenciaUbicacion = Validaciones.textoOpcional(referenciaUbicacion);
    }

    private static String validarDireccion(String direccion) {
        return Validaciones.textoObligatorio(direccion, "La dirección de visita es obligatoria");
    }

    // Getters
    public String getDireccionVisita() {
        return direccionVisita;
    }

    public String getReferenciaUbicacion() {
        return referenciaUbicacion;
    }

    public void actualizarDireccion(String direccionVisita, String referenciaUbicacion) {
        this.direccionVisita = validarDireccion(direccionVisita);
        this.referenciaUbicacion = Validaciones.textoOpcional(referenciaUbicacion);
    }

    public String obtenerUbicacionCompleta() {
        if (!tieneReferenciaUbicacion()) {
            return direccionVisita;
        }
        return direccionVisita + " - Referencia: " + referenciaUbicacion;
    }

    public boolean tieneReferenciaUbicacion() {
        return referenciaUbicacion != null;
    }

    public String mostrarDatosDomiciliarios() {
        return "Dirección: " + direccionVisita
                + "\nReferencia: " + (tieneReferenciaUbicacion() ? referenciaUbicacion : "No especificada");
    }

    @Override
    public Modalidad modalidad() {
        return Modalidad.DOMICILIARIA;
    }

    @Override
    public String detalleLugar() {
        return obtenerUbicacionCompleta();
    }

    @Override 
    public CitaDomiciliaria clonarCon(Long nuevoId, LocalDate fecha, LocalTime inicio, LocalTime fin) {
        return new CitaDomiciliaria.Builder()
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
                .direccionVisita(getDireccionVisita())
                .referenciaUbicacion(getReferenciaUbicacion())
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
        private String direccionVisita;
        private String referenciaUbicacion;

        public Builder id(Long id) {this.id = id;return this;}
        public Builder agendaMedica(AgendaMedica agendaMedica) {this.agendaMedica = agendaMedica;return this;}
        public Builder expediente(Expediente expediente) {this.expediente = expediente;return this;}
        public Builder orden(int orden) {this.orden = orden;return this;}
        public Builder estadoCita(EstadoCita estadoCita) {this.estadoCita = estadoCita;return this;}
        public Builder prioridad(Prioridad prioridad) {this.prioridad = prioridad;return this;}
        public Builder tipo(TipoCita tipo) {this.tipo = tipo;return this;}
        public Builder citaOrigen(Cita citaOrigen) {this.citaOrigen = citaOrigen;return this;}
        public Builder fechaCita(LocalDate fechaCita) {this.fechaCita = fechaCita;return this;}
        public Builder horaInicio(LocalTime horaInicio) {this.horaInicio = horaInicio;return this;}
        public Builder horaFin(LocalTime horaFin) {this.horaFin = horaFin;return this;}
        public Builder direccionVisita(String direccionVisita) {this.direccionVisita = direccionVisita;return this;}
        public Builder referenciaUbicacion(String referenciaUbicacion) {this.referenciaUbicacion = referenciaUbicacion;return this;}


        public CitaDomiciliaria build() {
            return new CitaDomiciliaria(
                id,
                agendaMedica,
                expediente,
                orden,
                estadoCita,
                prioridad,
                tipo,
                citaOrigen,
                fechaCita,
                horaInicio,
                horaFin,
                direccionVisita,
                referenciaUbicacion
            );
        } 
    }
}
