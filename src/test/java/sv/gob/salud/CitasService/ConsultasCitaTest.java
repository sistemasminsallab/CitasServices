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

/**
 * Consultas del servicio: listar todas las citas, filtrar por estado, contar por estado, buscar por
 * prioridad, listar las próximas (no finales) y listar agendas. Las consultas cronológicas devuelven
 * las citas ordenadas por fecha y hora de inicio, no en el orden en que las guardó el repositorio.
 */
class ConsultasCitaTest {

    private static final LocalDate DIA_UNO = LocalDate.now().plusDays(1);
    private static final LocalDate DIA_DOS = DIA_UNO.plusDays(1);

    private CitaServicio servicio;

    @BeforeEach
    void preparar() {
        RangoHorario jornada = new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0),
                Modalidad.PRESENCIAL);

        // Día 1: se insertan el de las 9:00 antes que el de las 8:00 a propósito.
        AgendaMedica agendaDiaUno = new AgendaMedica(1L, null, null, DIA_UNO, jornada, 6, true);
        agendaDiaUno.agendarPresencial(20L, null, Prioridad.ALTA, TipoCita.PRIMERA_VEZ,
                LocalTime.of(9, 0), LocalTime.of(9, 30), "Consultorio 1", null);
        agendaDiaUno.agendarPresencial(10L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(8, 0), LocalTime.of(8, 30), "Consultorio 1", null);

        // Día 2: una cita que se cancelará para probar el filtro y otra que quedará programada.
        AgendaMedica agendaDiaDos = new AgendaMedica(2L, null, null, DIA_DOS, jornada, 6, true);
        agendaDiaDos.agendarPresencial(30L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(8, 0), LocalTime.of(8, 30), "Consultorio 1", null);
        agendaDiaDos.agendarPresencial(40L, null, Prioridad.ALTA, TipoCita.PRIMERA_VEZ,
                LocalTime.of(9, 0), LocalTime.of(9, 30), "Consultorio 1", null);

        RepositorioMemoria repositorio = new RepositorioMemoria(List.of(agendaDiaUno, agendaDiaDos));
        servicio = new CitaServicio(repositorio, repositorio, List.of());
        servicio.cancelarCita(30L);
    }

    @Test
    void listarCitasDevuelveTodasSinFiltrarPorEstado() {
        assertEquals(4, servicio.listarCitas().size());
    }

    @Test
    void listarCitasPorEstadoFiltraLasQueCoinciden() {
        List<Long> programadas = ids(servicio.listarCitasPorEstado(EstadoCita.PROGRAMADA));

        assertEquals(List.of(10L, 20L, 40L), programadas);
        assertEquals(List.of(30L), ids(servicio.listarCitasPorEstado(EstadoCita.CANCELADA)));
    }

    @Test
    void listarCitasPorEstadoOrdenaCronologicamentePorFechaYHora() {
        List<Cita> programadas = servicio.listarCitasPorEstado(EstadoCita.PROGRAMADA);

        assertEquals(List.of(10L, 20L, 40L), ids(programadas));
        assertEquals(List.of(LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(9, 0)),
                programadas.stream().map(Cita::getHoraInicio).toList());
        assertEquals(List.of(DIA_UNO, DIA_UNO, DIA_DOS),
                programadas.stream().map(Cita::getFechaCita).toList());
    }

    @Test
    void listarCitasPorEstadoDevuelveListaVaciaSiNoHayCoincidencias() {
        assertTrue(servicio.listarCitasPorEstado(EstadoCita.NO_ASISTIO).isEmpty());
    }

    @Test
    void contarCitasPorEstadoCuentaSoloLasQueCoinciden() {
        assertEquals(3, servicio.contarCitasPorEstado(EstadoCita.PROGRAMADA));
        assertEquals(1, servicio.contarCitasPorEstado(EstadoCita.CANCELADA));
        assertEquals(0, servicio.contarCitasPorEstado(EstadoCita.ATENDIDA));
    }

    @Test
    void contarCitasPorEstadoReflejaLosCambiosDeEstado() {
        assertEquals(3, servicio.contarCitasPorEstado(EstadoCita.PROGRAMADA));

        servicio.confirmarCita(10L);

        assertEquals(2, servicio.contarCitasPorEstado(EstadoCita.PROGRAMADA));
        assertEquals(1, servicio.contarCitasPorEstado(EstadoCita.CONFIRMADA));
    }

    @Test
    void lasConsultasPorEstadoRechazanUnEstadoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> servicio.listarCitasPorEstado(null));
        assertThrows(IllegalArgumentException.class,
                () -> servicio.contarCitasPorEstado(null));
    }

    @Test
    void buscarPorPrioridadFiltraYOrdenaCronologicamente() {
        assertEquals(List.of(20L, 40L), ids(servicio.buscarPorPrioridad(Prioridad.ALTA)));
        assertEquals(List.of(10L, 30L), ids(servicio.buscarPorPrioridad(Prioridad.NORMAL)));
    }

    @Test
    void buscarPorPrioridadNoFiltraPorEstado() {
        // La 30 está CANCELADA y aun así aparece: esta consulta filtra por prioridad, no por estado.
        assertTrue(ids(servicio.buscarPorPrioridad(Prioridad.NORMAL)).contains(30L));
    }

    @Test
    void buscarPorPrioridadRechazaUnaPrioridadNula() {
        assertThrows(IllegalArgumentException.class, () -> servicio.buscarPorPrioridad(null));
    }

    @Test
    void proximasCitasExcluyeLosEstadosFinales() {
        // La 30 ya está CANCELADA desde el setup; estas dos pasan a estados finales.
        servicio.confirmarCita(40L);
        servicio.atenderCita(40L);                 // ATENDIDA
        servicio.confirmarCita(20L);
        servicio.marcarNoAsistio(20L);             // NO_ASISTIO

        assertEquals(List.of(10L), ids(servicio.proximasCitas()));
        assertTrue(EstadoCita.ATENDIDA.esFinal());
        assertTrue(EstadoCita.NO_ASISTIO.esFinal());
        assertTrue(EstadoCita.CANCELADA.esFinal());
    }

    @Test
    void proximasCitasOrdenaCronologicamente() {
        // Quedan vigentes la 10 y la 20 (día 1) y la 40 (día 2); la 30 está cancelada.
        assertEquals(List.of(10L, 20L, 40L), ids(servicio.proximasCitas()));
    }

    @Test
    void listarAgendasDevuelveTodasLasAgendas() {
        assertEquals(List.of(1L, 2L),
                servicio.listarAgendas().stream().map(AgendaMedica::getId).toList());
    }

    private static List<Long> ids(List<Cita> citas) {
        return citas.stream().map(Cita::getId).toList();
    }
}
