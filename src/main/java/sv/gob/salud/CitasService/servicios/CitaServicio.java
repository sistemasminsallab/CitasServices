package sv.gob.salud.CitasService.servicios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
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

/**
 * Fabricación pura: orquesta casos de uso; las reglas de negocio siguen en el
 * dominio.
 */
@Service
public class CitaServicio implements OperacionesCita {

    /** Orden cronológico: primero por fecha y, dentro del día, por hora de inicio. */
    private static final Comparator<Cita> POR_FECHA_Y_HORA =
            Comparator.comparing(Cita::getFechaCita).thenComparing(Cita::getHoraInicio);

    private final CitaRepositorio citas;
    private final AgendaRepositorio agendas;
    private final List<ObservadorCita> observadores;

    public CitaServicio(CitaRepositorio citas, AgendaRepositorio agendas, List<ObservadorCita> observadores) {
        this.citas = citas;
        this.agendas = agendas;
        this.observadores = observadores;
    }

    @Override
    public List<Cita> listarCitas() {
        return citas.buscarCitas();
    }

    @Override
    public List<Cita> listarCitasPorEstado(EstadoCita estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        return citas.buscarCitas().stream()
                .filter(cita -> cita.getEstadoCita() == estado)
                .sorted(POR_FECHA_Y_HORA)
                .toList();
    }

    @Override
    public long contarCitasPorEstado(EstadoCita estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        return citas.buscarCitas().stream()
                .filter(cita -> cita.getEstadoCita() == estado)
                .count();
    }

    @Override
    public List<Cita> buscarPorPrioridad(Prioridad prioridad) {
        if (prioridad == null) {
            throw new IllegalArgumentException("La prioridad no puede ser nula");
        }
        return citas.buscarCitas().stream()
                .filter(cita -> cita.getPrioridad() == prioridad)
                .sorted(POR_FECHA_Y_HORA)
                .toList();
    }

    @Override
    public List<Cita> proximasCitas() {
        return citas.buscarCitas().stream()
                .filter(cita -> !cita.getEstadoCita().esFinal())
                .sorted(POR_FECHA_Y_HORA)
                .toList();
    }

    @Override
    public Cita obtenerCita(Long id) {
        return citas.buscarCitaPorId(id)
                .orElseThrow(() -> new CitaNoEncontradaException("No existe la cita " + id));
    }

    @Override
    public List<AgendaMedica> listarAgendas() {
        return agendas.buscarAgendas();
    }

    @Override
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

    /**
     * Sujeto del Observer: confirma la cita y notifica a todos los observadores.
     * El servicio no conoce las clases concretas; solo recorre la lista (OCP).
     */
    @Override
    public Cita confirmarCita(Long id) {
        return cambiarEstado(id, EstadoCita.CONFIRMADA);
    }

    @Override
    public Cita cancelarCita(Long id) {
        return cambiarEstado(id, EstadoCita.CANCELADA);
    }

    @Override
    public Cita atenderCita(Long id) {
        return cambiarEstado(id, EstadoCita.ATENDIDA);
    }

    @Override
    public Cita marcarNoAsistio(Long id) {
        return cambiarEstado(id, EstadoCita.NO_ASISTIO);
    }

    @Override
    public Cita cambiarEstadoCita(Long id, EstadoCita nuevoEstado) {
        return cambiarEstado(id, nuevoEstado);
    }

    @Override
    public Cita reprogramarCita(Long id, LocalDate fecha, LocalTime inicio, LocalTime fin) {
        Cita cita = obtenerCita(id);
        cita.reprogramarCita(fecha, inicio, fin); // mueve el horario (valida no-cancelada y horario)
        return aplicarTransicion(cita, EstadoCita.REPROGRAMADA);
    }

    /**
     * Sujeto del Observer: aplica la transición de estado a la cita y notifica a los observadores.
     * Un solo lugar concentra "cambiar estado + avisar", así cada caso de uso es una línea y la
     * máquina de estados (EstadoCita) rechaza las transiciones ilegales.
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
