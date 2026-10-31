package sv.gob.salud.CitasService.notificaciones;

import org.springframework.stereotype.Component;

import sv.gob.salud.CitasService.modelos.entidades.Cita;

/**
 * Observador concreto: avisa por correo cuando la cita se confirma.
 *
 * Al ser un {@code @Component}, Spring lo detecta y lo inyecta en la lista de observadores
 * de {@link sv.gob.salud.CitasService.servicios.CitaServicio} sin configuración extra.
 * Aquí solo imprime; el envío real de correo llega en un módulo posterior.
 */
@Component
public class ObservadorCorreo implements ObservadorCita {

    @Override
    public void alConfirmar(Cita cita) {
        System.out.println("[correo] Cita " + cita.getId() + " confirmada; se notifica al paciente");
    }
}
