package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.entidades.RangoHorario;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.repositorios.RepositorioMemoria;
import sv.gob.salud.CitasService.servicios.CitaServicio;

/** Consultas de solo lectura del servicio de citas (filtrado y orden con streams). */
class ConsultasCitaTest {

    private CitaServicio servicio;

    @BeforeEach
    void preparar() {
        RangoHorario jornada = new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
        AgendaMedica agenda = new AgendaMedica(1L, null, null, LocalDate.now().plusDays(1), jornada, 6, true);
        agenda.agendarPresencial(10L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(8, 0), LocalTime.of(8, 30), "Consultorio 1", null);
        agenda.agendarVirtual(11L, null, Prioridad.ALTA, TipoCita.SUBSECUENTE,
                LocalTime.of(9, 0), LocalTime.of(9, 30), "https://meet.minsal.gob.sv/x", "Meet");
        RepositorioMemoria repositorio = new RepositorioMemoria(List.of(agenda));
        servicio = new CitaServicio(repositorio, repositorio, List.of());
    }

    @Test
    void todasNacenProgramadas() {
        assertEquals(2, servicio.buscarPorEstado(EstadoCita.PROGRAMADA).size());
        assertTrue(servicio.buscarPorEstado(EstadoCita.CONFIRMADA).isEmpty());
    }

    @Test
    void alConfirmarUnaCambiaElConteoPorEstado() {
        servicio.confirmarCita(10L);
        assertEquals(1, servicio.buscarPorEstado(EstadoCita.PROGRAMADA).size());
        assertEquals(1, servicio.buscarPorEstado(EstadoCita.CONFIRMADA).size());
    }

    @Test
    void citasDeAgendaDevuelveLasDosOrdenadasPorId() {
        List<Cita> citas = servicio.citasDeAgenda(1L);
        assertEquals(2, citas.size());
        assertEquals(10L, citas.get(0).getId());
        assertEquals(11L, citas.get(1).getId());
    }

    @Test
    void cuposDisponiblesDescuentaLasAgendadas() {
        assertEquals(4, servicio.cuposDisponibles(1L)); // cupo 6 - 2 citas
    }

    @Test
    void agendaInexistenteLanzaExcepcion() {
        assertThrows(RuntimeException.class, () -> servicio.citasDeAgenda(999L));
    }

    @Test
    void proximasCitasTraeLasDeHoyEnAdelante() {
        assertEquals(2, servicio.proximasCitas().size()); // ambas son de mañana
    }

}
