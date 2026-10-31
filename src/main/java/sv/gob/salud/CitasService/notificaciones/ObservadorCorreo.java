package sv.gob.salud.CitasService.notificaciones;

import org.springframework.stereotype.Component;

import sv.gob.salud.CitasService.modelos.entidades.Cita;

/**
 * Observador concreto: avisa por correo cuando la cita se confirma.
 * Aquí solo imprime; el envío real de correo llega en un módulo posterior.
 */
@Component
public class ObservadorCorreo implements ObservadorCita{
 @Override 
  public void alConfirmar(Cita cita) {
     System.out.println("[correo] Cita " + cita.getId() + " confirmada; se notifica al paciente");   
  }
}
