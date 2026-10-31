package sv.gob.salud.CitasService.notificaciones;

import sv.gob.salud.CitasService.modelos.entidades.Cita;

public interface ObservadorCita {
  void alConfirmar(Cita cita);
}
