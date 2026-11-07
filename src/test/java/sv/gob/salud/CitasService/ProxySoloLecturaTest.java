package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.RangoHorario;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.registro.ProxyCita;
import sv.gob.salud.CitasService.repositorios.RepositorioMemoria;
import sv.gob.salud.CitasService.servicios.CitaServicio;
import sv.gob.salud.CitasService.servicios.OperacionesCita;

/**
 * Modo solo lectura: el proxy deja pasar las consultas y bloquea las escrituras, sin tocar el
 * servicio real.
 */
class ProxySoloLecturaTest {

    private CitaServicio real;
    private OperacionesCita soloLectura;

    @BeforeEach
    void preparar() {
        RangoHorario jornada = new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
        AgendaMedica agenda = new AgendaMedica(1L, null, null, LocalDate.now().plusDays(1), jornada, 6, true);
        agenda.agendarPresencial(10L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(8, 0), LocalTime.of(8, 30), "Consultorio 1", null);
        RepositorioMemoria repositorio = new RepositorioMemoria(List.of(agenda));
        real = new CitaServicio(repositorio, repositorio, List.of());
        soloLectura = ProxyCita.soloLectura(real);
    }

    @Test
    void permiteLasConsultas() {
        // Una lectura no se bloquea y devuelve lo mismo que el servicio real.
        assertDoesNotThrow(() -> soloLectura.listarCitas());
        assertEquals(real.listarCitas().size(), soloLectura.listarCitas().size());
    }

    @Test
    void bloqueaLasEscrituras() {
        // confirmarCita es escritura: debe bloquearse antes de tocar el servicio real.
        assertThrows(IllegalStateException.class, () -> soloLectura.confirmarCita(10L));
    }
}