package sv.gob.salud.CitasService.notificaciones;

import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;

public interface ObservadorCita {
  void alConfirmar(Cita cita);

  /**
   * @param cita
   * @param nuevoEstado
   */
  void alCambiarEstado(Cita cita, EstadoCita nuevoEstado);
}
