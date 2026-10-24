package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.modelos.entidades.CitaPresencial;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;

public class BuilderCitaTest {
  @Test
  void builderProduceEquivalenteAlConstructor() {
    CitaPresencial cita = new CitaPresencial.Builder()
    .id(1L)
    .orden(1)
    .estadoCita(EstadoCita.PROGRAMADA)
    .prioridad(Prioridad.NORMAL)
    .tipo(TipoCita.PRIMERA_VEZ)
    .fechaCita(LocalDate.now().plusDays(1))
    .horaInicio(LocalTime.of(9, 0))
    .consultorio("C-203")
    .indicaciones("Ayuno de 8 horas")
    .build();

    assertEquals("C-203", cita.getConsultorio());
    assertEquals(1L, cita.getId());
  }

  @Test
  void builderSiguePropagandoLasValidacionesDelConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new CitaPresencial.Builder()
      .id(1L)
      .orden(0)
      .build());
  }
}
