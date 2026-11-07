package sv.gob.salud.CitasService.notificaciones;

import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;

/**
 * Observer: contrato que cumple todo el que quiera reaccionar cuando una cita cambia de estado.
 *
 * El servicio que cambia el estado de la cita (el "sujeto") notifica a todos los observadores sin
 * conocer sus clases concretas: así se agrega un nuevo observador sin tocar el servicio (OCP)
 * y el emisor queda desacoplado de los receptores (bajo acoplamiento, GRASP).
 *
 * En M6 este mismo patrón lo da Spring con su sistema de eventos
 * ({@code ApplicationEventPublisher} + {@code @EventListener}).
 */
public interface ObservadorCita {

    /**
     * Reacciona a un cambio de estado de la cita (confirmación, cancelación, reprogramación, etc.).
     *
     * @param cita        la cita afectada
     * @param nuevoEstado el estado al que acaba de pasar
     */
    void alCambiarEstado(Cita cita, EstadoCita nuevoEstado);
}
