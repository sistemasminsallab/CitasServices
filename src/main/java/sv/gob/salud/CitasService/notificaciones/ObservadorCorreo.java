package sv.gob.salud.CitasService.notificaciones;

import org.springframework.stereotype.Component;

import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;

@Component 
public class ObservadorCorreo implements ObservadorCita {
  @Override
  public void alConfirmar(Cita cita) {
    System.out.println("[correo] Cita " + cita.getId() + " Confirmada; se notifica al paciente.");
  }

  @Override
  public void alCambiarEstado(Cita cita, EstadoCita nuevoEstado) {
      System.out.println("[correo] Cita " + cita.getId() + " cambió a " + nuevoEstado
              + "; se notifica al paciente");
  }
}
