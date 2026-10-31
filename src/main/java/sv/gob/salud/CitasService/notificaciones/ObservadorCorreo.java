package sv.gob.salud.CitasService.notificaciones;

import org.springframework.stereotype.Component;

import sv.gob.salud.CitasService.modelos.entidades.Cita;

@Component 
public class ObservadorCorreo implements ObservadorCita {
  @Override
  public void alConfirmar(Cita cita) {
    System.out.println("[correo] Cita " + cita.getId() + " Confirmada; se notifica al paciente.");
  }
}
