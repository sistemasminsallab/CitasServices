package sv.gob.salud.CitasService.notificaciones;

import org.springframework.stereotype.Component;

import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;

@Component
public class ObservadorAuditoria implements ObservadorCita {
  @Override
  public void alConfirmar(Cita cita) {
    System.out.println("[auditoria] Cita " + cita.getId() + " paso a estado " + cita.getEstadoCita());
  }

  @Override
  public void alCambiarEstado(Cita cita, EstadoCita nuevoEstado) {
      System.out.println("[auditoría] Cita " + cita.getId() + " pasó a estado " + nuevoEstado);
  }
}
