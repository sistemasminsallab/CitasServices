package sv.gob.salud.CitasService.notificaciones;

import org.springframework.stereotype.Component;

import sv.gob.salud.CitasService.modelos.entidades.Cita;

/**
 * Observador concreto: deja rastro de auditoría cuando la cita se confirma.
 * Demuestra el valor del patrón: agregarlo NO tocó ni el servicio ni el observador de correo.
 */
@Component
public class ObservadorAuditoria implements ObservadorCita {

    @Override
    public void alConfirmar(Cita cita) {
        System.out.println("[auditoría] Cita " + cita.getId() + " pasó a estado " + cita.getEstadoCita());
    }
}