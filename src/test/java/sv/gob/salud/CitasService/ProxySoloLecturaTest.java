package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.dto.AgendarCitaSolicitud;
import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.RangoHorario;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.registro.ProxyCita;
import sv.gob.salud.CitasService.repositorios.RepositorioMemoria;
import sv.gob.salud.CitasService.servicios.CitaServicio;
import sv.gob.salud.CitasService.servicios.OperacionesCita;

/** Verifica que el modo mantenimiento permita consultar y rechace todas las escrituras. */
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
        // Las tres consultas conservan los resultados del servicio real.
        assertEquals(real.listarCitas(), soloLectura.listarCitas());
        assertSame(real.obtenerCita(10L), soloLectura.obtenerCita(10L));
        assertEquals(real.listarAgendas(), soloLectura.listarAgendas());

        // Estos métodos también pasan por el handler, pero no deben tratarse como escrituras.
        assertDoesNotThrow(() -> soloLectura.toString());
        assertDoesNotThrow(() -> soloLectura.hashCode());
        assertDoesNotThrow(() -> soloLectura.equals(real));
    }

    @Test
    void bloqueaLasEscrituras() {
        AgendarCitaSolicitud solicitud = new AgendarCitaSolicitud(1L, Modalidad.PRESENCIAL,
                LocalTime.of(9, 0), LocalTime.of(9, 30));
        var cita = real.obtenerCita(10L);
        LocalDate fechaOriginal = cita.getFechaCita();

        // Cada operación de escritura se rechaza con el mensaje del aspecto de mantenimiento.
        var error = assertThrows(IllegalStateException.class, () -> soloLectura.confirmarCita(10L));
        assertEquals("Sistema en modo solo lectura: no se permite 'confirmarCita'.", error.getMessage());
        assertThrows(IllegalStateException.class, () -> soloLectura.agendar(solicitud));
        assertThrows(IllegalStateException.class, () -> soloLectura.cancelarCita(10L));
        assertThrows(IllegalStateException.class, () -> soloLectura.atenderCita(10L));
        assertThrows(IllegalStateException.class, () -> soloLectura.marcarNoAsistio(10L));
        assertThrows(IllegalStateException.class, () -> soloLectura.reprogramarCita(10L,
                fechaOriginal.plusDays(1), LocalTime.of(10, 0), LocalTime.of(10, 30)));

        // Además de lanzar la excepción, el proxy debe dejar intacta la información original.
        assertEquals(1, real.listarCitas().size());
        assertEquals(EstadoCita.PROGRAMADA, cita.getEstadoCita());
        assertEquals(fechaOriginal, cita.getFechaCita());
    }
}
