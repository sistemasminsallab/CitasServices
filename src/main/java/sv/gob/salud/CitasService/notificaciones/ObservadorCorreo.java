package sv.gob.salud.CitasService.notificaciones;

import org.springframework.stereotype.Component;

import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;

/**
 * Observador concreto: avisa por correo cuando la cita cambia de estado.
 *
 * Al ser un {@code @Component}, Spring lo detecta y lo inyecta en la lista de observadores
 * de {@link sv.gob.salud.CitasService.servicios.CitaServicio} sin configuración extra.
 * Aquí solo imprime; el envío real de correo llega en un módulo posterior.
 */
@Component
public class ObservadorCorreo implements ObservadorCita {
  static boolean notificado = false;
  static Cita recibida;

  @Override
  public void alCambiarEstado(Cita cita, EstadoCita nuevoEstado) {
    notificado = true;
    recibida = cita;
    System.out.println("[correo] Cita " + cita.getId() + " cambió a " + nuevoEstado
            + "; se notifica al paciente");
  }
}
