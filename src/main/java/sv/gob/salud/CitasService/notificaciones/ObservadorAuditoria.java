package sv.gob.salud.CitasService.notificaciones;

import org.springframework.stereotype.Component;

import sv.gob.salud.CitasService.modelos.entidades.Cita;

@Component
public class ObservadorAuditoria implements ObservadorCita {
  @Override
  public void alConfirmar(Cita cita) {
    System.out.println("[auditoria] Cita " + cita.getId() + " paso a estado " + cita.getEstadoCita());
  }
}
