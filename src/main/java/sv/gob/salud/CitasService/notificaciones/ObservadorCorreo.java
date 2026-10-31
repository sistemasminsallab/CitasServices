package sv.gob.salud.CitasService.notificaciones;

import org.springframework.stereotype.Component;

import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;

/**
 * Observador concreto: avisa por correo cuando la cita se confirma.
 * Aquí solo imprime; el envío real de correo llega en un módulo posterior.
 */
@Component
public class ObservadorCorreo implements ObservadorCita {


    @Override
public void alCambiarEstado(Cita cita, EstadoCita nuevoEstado) {
    System.out.println("[correo] Cita " + cita.getId() + " cambió a " + nuevoEstado
            + "; se notifica al paciente");
}
}