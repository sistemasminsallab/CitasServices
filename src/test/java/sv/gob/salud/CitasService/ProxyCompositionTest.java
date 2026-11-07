package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.RangoHorario;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.registro.ProxyCita;
import sv.gob.salud.CitasService.registro.RegistroOperacion;
import sv.gob.salud.CitasService.repositorios.RepositorioMemoria;
import sv.gob.salud.CitasService.servicios.CitaServicio;
import sv.gob.salud.CitasService.servicios.OperacionesCita;

class ProxyCompositionTest {
  private CitaServicio real;

  @BeforeEach
  void preparar() {
    RangoHorario jornada = new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
    AgendaMedica agenda = new AgendaMedica(1L, null, null, LocalDate.now().plusDays(1), jornada, 6, true);
    agenda.agendarPresencial(10L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
            LocalTime.of(8, 0), LocalTime.of(8, 30), "Consultorio 1", null);
    RepositorioMemoria repo = new RepositorioMemoria(List.of(agenda));
    real = new CitaServicio(repo, repo, List.of());
  }

  @Test
  void registraYBloqueaALaVez() {
    List<RegistroOperacion> bitacora = new ArrayList<>();

    // Se apilan: el real va dentro de solo-lectura, y eso dentro de registro.
    OperacionesCita protegido = ProxyCita.soloLectura(real);               // interno: bloquea
    OperacionesCita apilado = ProxyCita.conRegistro(protegido, bitacora::add); // externo: registra

    apilado.listarCitas();  // lectura: pasa por ambos y queda registrada
    // escritura: el proxy interno la bloquea; el externo igual registra el intento
    assertThrows(IllegalStateException.class, () -> apilado.confirmarCita(10L));

    assertEquals(2, bitacora.size());
    assertEquals("listarCitas", bitacora.get(0).metodo());
    assertEquals("confirmarCita", bitacora.get(1).metodo());
  }
}
