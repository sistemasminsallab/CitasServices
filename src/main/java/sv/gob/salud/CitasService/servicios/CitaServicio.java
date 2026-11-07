package sv.gob.salud.CitasService.servicios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import sv.gob.salud.CitasService.dto.AgendarCitaSolicitud;
import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.modelos.excepciones.CitaNoEncontradaException;
import sv.gob.salud.CitasService.notificaciones.ObservadorCita;
import sv.gob.salud.CitasService.repositorios.AgendaRepositorio;
import sv.gob.salud.CitasService.repositorios.CitaRepositorio;

/** Fabricación pura: orquesta casos de uso; las reglas de negocio siguen en el dominio. */
@Service
public class CitaServicio implements OperacionesCita {
    private final CitaRepositorio citas;
    private final AgendaRepositorio agendas;
    /** Observer: Spring inyecta aquí todos los beans que implementan ObservadorCita. */
    private final List<ObservadorCita> observadores;

    public CitaServicio(CitaRepositorio citas, AgendaRepositorio agendas, List<ObservadorCita> observadores) {
        this.citas = citas;
        this.agendas = agendas;
        this.observadores = observadores;
    }

    public List<Cita> listarCitas() {
        return citas.buscarCitas();
    }

    public Cita obtenerCita(Long id) {
        return citas.buscarCitaPorId(id)
                .orElseThrow(() -> new CitaNoEncontradaException("No existe la cita " + id));
    }

    public List<AgendaMedica> listarAgendas() {
        return agendas.buscarAgendas();
    }

    public Cita agendar(AgendarCitaSolicitud solicitud) {
        AgendaMedica agenda = agendas.buscarAgendaPorId(solicitud.agendaId())
                .orElseThrow(() -> new CitaNoEncontradaException("No existe la agenda " + solicitud.agendaId()));
        Long id = citas.siguienteId();
        if (solicitud.modalidad() == Modalidad.VIRTUAL) {
            return agenda.agendarVirtual(id, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                    solicitud.horaInicio(), solicitud.horaFin(), "https://meet.minsal.gob.sv/" + id, "Meet");
        }
        return agenda.agendarPresencial(id, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                solicitud.horaInicio(), solicitud.horaFin(), "Consultorio 1", null);
    }

    public Cita confirmarCita(Long id) {
        return cambiarEstado(id, EstadoCita.CONFIRMADA);
    }

    public Cita cancelarCita(Long id) {
        return cambiarEstado(id, EstadoCita.CANCELADA);
    }

    public Cita atenderCita(Long id) {
        return cambiarEstado(id, EstadoCita.ATENDIDA);
    }

    public Cita marcarNoAsistio(Long id) {
        return cambiarEstado(id, EstadoCita.NO_ASISTIO);
    }

    public Cita reprogramarCita(Long id, LocalDate fecha, LocalTime inicio, LocalTime fin) {
        Cita cita = obtenerCita(id);
        cita.reprogramarCita(fecha, inicio, fin); // mueve el horario (valida no-cancelada y horario)
        return aplicarTransicion(cita, EstadoCita.REPROGRAMADA);
    }

    /**
     * Sujeto del Observer: aplica la transición de estado a la cita y notifica a los observadores.
     * Un solo lugar concentra "cambiar estado + avisar", así cada caso de uso es una línea y la
     * máquina de estados ({@link EstadoCita}) rechaza las transiciones ilegales.
     */
    private Cita cambiarEstado(Long id, EstadoCita nuevoEstado) {
        return aplicarTransicion(obtenerCita(id), nuevoEstado);
    }

    private Cita aplicarTransicion(Cita cita, EstadoCita nuevoEstado) {
        cita.cambiarEstadoCita(nuevoEstado);
        observadores.forEach(observador -> observador.alCambiarEstado(cita, nuevoEstado));
        return cita;
    }
}
