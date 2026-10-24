package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.modelos.entidades.*;
import sv.gob.salud.CitasService.modelos.enums.*;
import sv.gob.salud.CitasService.modelos.fabricas.*;

public class FactoryCitaTest {
  private DatosCita datos(String consultorio, String url, String plataforma) {
    return new DatosCita(
      1L, null, null, 1,
      EstadoCita.PROGRAMADA, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ, null,
      LocalDate.now().plusDays(1), LocalTime.of(8, 0), LocalTime.of(9,0),
      consultorio, null, null,
      null, null,
      url, plataforma);
  }

  @Test
  void creaCitaPresencialSegunLaModalidad() {
    Cita cita = CitaFactory.crear(Modalidad.PRESENCIAL, datos("C-203", null, null));

    CitaPresencial presencial = assertInstanceOf(CitaPresencial.class, cita);
    assertEquals("C-203", presencial.getConsultorio());
  }

  @Test
  void creaCitaVirtualSegunLaModalidad() {
    Cita cita = CitaFactory.crear(Modalidad.VIRTUAL, datos(null, "http://meet.example/abc", "Zoom"));

    CitaVirtual virtual = assertInstanceOf(CitaVirtual.class, cita);
    assertEquals("http://meet.example/abc", virtual.getUrl());
  }
}
