package sv.gob.salud.CitasService.notificaciones;

import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;

/**
 * Observer: contrato que cumple todo el que quiera reaccionar cuando una cita cambia de estado.
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