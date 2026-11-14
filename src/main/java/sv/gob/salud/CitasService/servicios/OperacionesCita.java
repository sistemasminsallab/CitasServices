package sv.gob.salud.CitasService.servicios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import sv.gob.salud.CitasService.dto.AgendarCitaSolicitud;
import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.excepciones.CitaNoDisponibleException;
import sv.gob.salud.CitasService.modelos.excepciones.CitaNoEncontradaException;

/**
 * Contrato de los casos de uso de citas (puerto de entrada del servicio).
 *
 * <p>Existe para que un Proxy dinámico ({@link java.lang.reflect.Proxy}) pueda envolver al servicio
 * real: los proxies dinámicos del JDK se construyen sobre una interfaz, no sobre una clase concreta.
 * Extraer esta interfaz también desacopla a los clientes de la implementación (DIP), el mismo
 * contrato sobre el que Spring creará sus proxies de AOP en el Módulo 6.
 *
 * <h2>Consultas y comandos</h2>
 * <p>Las consultas ({@link #listarCitas()}, {@link #listarCitasPorEstado(EstadoCita)},
 * {@link #contarCitasPorEstado(EstadoCita)}, {@link #buscarPorPrioridad(Prioridad)},
 * {@link #proximasCitas()}, {@link #obtenerCita(Long)}, {@link #listarAgendas()}) no modifican
 * estado y empiezan siempre con {@code listar}, {@code obtener}, {@code contar} o {@code buscar}.
 * La convención no es cosmética: {@code ManejadorSoloLectura} la usa para decidir qué operaciones
 * deja pasar en modo mantenimiento. Todo método nuevo que solo consulte debe respetarla; cualquier
 * otro se considera comando y queda bloqueado en ese modo.
 *
 * <h2>Errores esperados</h2>
 * <ul>
 *   <li>{@link CitaNoEncontradaException}: no existe la cita o la agenda indicada.</li>
 *   <li>{@link CitaNoDisponibleException}: la agenda está inactiva o sin cupo, la cita queda fuera
 *       de horario o se solapa con otra.</li>
 *   <li>{@link IllegalStateException}: transición de estado ilegal o intento de reprogramar una
 *       cita cancelada.</li>
 *   <li>{@link IllegalArgumentException}: fecha u horas obligatorias ausentes, o inicio no
 *       anterior al fin.</li>
 * </ul>
 *
 * <p>Ningún método devuelve {@code null}: cuando no encuentra la cita o la agenda, lanza
 * {@link CitaNoEncontradaException}.
 */
public interface OperacionesCita {

    // --- Consultas (lectura: no las bloquea el modo solo lectura) ---

    /** Devuelve todas las citas registradas, sin filtrar por estado ni por agenda. */
    List<Cita> listarCitas();

    /**
     * Devuelve las citas que están en el estado indicado, ordenadas cronológicamente por fecha y
     * hora de inicio.
     *
     * @throws IllegalArgumentException si {@code estado} es {@code null}
     */
    List<Cita> listarCitasPorEstado(EstadoCita estado);

    /** Cuenta cuántas citas están en el estado indicado. */
    long contarCitasPorEstado(EstadoCita estado);

    /**
     * Devuelve las citas de la prioridad indicada, ordenadas cronológicamente por fecha y hora de
     * inicio. No filtra por estado: una cita cancelada también aparece.
     *
     * @throws IllegalArgumentException si {@code prioridad} es {@code null}
     */
    List<Cita> buscarPorPrioridad(Prioridad prioridad);

    /**
     * Devuelve las citas que siguen vigentes, es decir, las que no están en un estado final
     * ({@link EstadoCita#esFinal()}: {@code CANCELADA}, {@code NO_ASISTIO} o {@code ATENDIDA}),
     * ordenadas cronológicamente por fecha y hora de inicio.
     */
    List<Cita> proximasCitas();

    /**
     * Busca una cita por su id.
     *
     * @throws CitaNoEncontradaException si no existe una cita con ese id
     */
    Cita obtenerCita(Long id);

    /** Devuelve todas las agendas médicas registradas, activas e inactivas. */
    List<AgendaMedica> listarAgendas();

    // --- Comandos (escritura: bloqueados en modo solo lectura) ---

    /**
     * Agenda una cita nueva en la agenda indicada, en estado {@code PROGRAMADA}.
     *
     * <p>La modalidad decide el tipo concreto: {@code VIRTUAL} crea una {@code CitaVirtual} con
     * enlace de videollamada; cualquier otra modalidad crea una {@code CitaPresencial}.
     *
     * @throws CitaNoEncontradaException si no existe la agenda indicada
     * @throws CitaNoDisponibleException si la agenda está inactiva o sin cupo, el horario queda
     *                                   fuera de su rango, o la cita se solapa con otra
     */
    Cita agendar(AgendarCitaSolicitud solicitud);

    /**
     * Confirma la cita: pasa a {@code CONFIRMADA} desde {@code PROGRAMADA} o {@code REPROGRAMADA}.
     *
     * <p>Notifica a los observadores registrados (correo, auditoría, etc.).
     *
     * @throws CitaNoEncontradaException si no existe la cita
     * @throws IllegalStateException     si la transición no es legal (la cita ya está en un estado
     *                                   final)
     */
    Cita confirmarCita(Long id);

    /**
     * Cancela la cita. {@code CANCELADA} es un estado final: ya no admite más transiciones.
     *
     * @throws CitaNoEncontradaException si no existe la cita
     * @throws IllegalStateException     si la transición no es legal (la cita ya está en un estado
     *                                   final)
     */
    Cita cancelarCita(Long id);

    /**
     * Marca la cita como atendida. Solo es legal desde {@code CONFIRMADA}; es estado final.
     *
     * @throws CitaNoEncontradaException si no existe la cita
     * @throws IllegalStateException     si la transición no es legal
     */
    Cita atenderCita(Long id);

    /**
     * Marca que el paciente no asistió. Solo es legal desde {@code CONFIRMADA}; es estado final.
     *
     * @throws CitaNoEncontradaException si no existe la cita
     * @throws IllegalStateException     si la transición no es legal
     */
    Cita marcarNoAsistio(Long id);

    /**
     * Cambia la cita al estado indicado aplicando la máquina de estados de {@link EstadoCita}.
     *
     * <p>Comando genérico: los métodos con nombre propio ({@link #confirmarCita(Long)},
     * {@link #cancelarCita(Long)}, etc.) son atajos para las transiciones habituales. Este deja
     * expresar cualquier transición legal sin ampliar el contrato y también notifica a los
     * observadores.
     *
     * @throws CitaNoEncontradaException si no existe la cita
     * @throws IllegalArgumentException  si {@code nuevoEstado} es {@code null}
     * @throws IllegalStateException     si la transición no es legal desde el estado actual
     */
    Cita cambiarEstadoCita(Long id, EstadoCita nuevoEstado);

    /**
     * Mueve la cita a otra fecha y horario y la deja en estado {@code REPROGRAMADA}.
     *
     * @param fecha  nueva fecha de la cita
     * @param inicio nueva hora de inicio
     * @param fin    nueva hora de fin, posterior a {@code inicio}
     * @throws CitaNoEncontradaException si no existe la cita
     * @throws IllegalStateException     si la cita ya está cancelada o en un estado final
     * @throws IllegalArgumentException  si falta la fecha o las horas, o {@code inicio} no es
     *                                   anterior a {@code fin}
     */
    Cita reprogramarCita(Long id, LocalDate fecha, LocalTime inicio, LocalTime fin);
}
