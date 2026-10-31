package sv.gob.salud.CitasService.notificaciones;

import sv.gob.salud.CitasService.modelos.entidades.Cita;

/**
 * Observer: contrato que cumple todo el que quiera reaccionar cuando una cita se confirma.
 *
 * El servicio que confirma la cita (el "sujeto") notifica a todos los observadores sin
 * conocer sus clases concretas: así se agrega un nuevo observador sin tocar el servicio (OCP)
 * y el emisor queda desacoplado de los receptores (bajo acoplamiento, GRASP).
 *
 * En M6 este mismo patrón lo da Spring con su sistema de eventos
 * ({@code ApplicationEventPublisher} + {@code @EventListener}).
 */
public interface ObservadorCita {

    /** Reacciona a la confirmación de una cita (enviar correo, registrar auditoría, etc.). */
    void alConfirmar(Cita cita);
}
