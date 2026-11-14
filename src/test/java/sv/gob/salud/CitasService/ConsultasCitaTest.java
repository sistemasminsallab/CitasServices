package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.*;

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
import sv.gob.salud.CitasService.modelos.excepciones.CitaNoEncontradaException;
import sv.gob.salud.CitasService.repositorios.RepositorioMemoria;
import sv.gob.salud.CitasService.servicios.CitaServicio;
import sv.gob.salud.CitasService.servicios.OperacionesCita;

class ConsultasCitaTest {
    private OperacionesCita servicio;
    private AgendaMedica agenda;
    private LocalDate hoy;
    private int notificaciones;

    @BeforeEach
    void preparar() {
        hoy = LocalDate.now();
        agenda = agenda(1L, hoy.plusDays(1));
        // Se insertan en orden inverso para comprobar la ordenación por id.
        agenda.agendarVirtual(11L, null, Prioridad.ALTA, TipoCita.SUBSECUENTE,
                LocalTime.of(9, 0), LocalTime.of(9, 30), "https://meet.minsal.gob.sv/x", "Meet");
        agregarCita(agenda, 10L, 8);
        servicio = servicio(List.of(agenda));
    }

    @Test
    void todasNacenProgramadas() {
        assertEquals(2, servicio.buscarPorEstado(EstadoCita.PROGRAMADA).size());
        assertTrue(servicio.buscarPorEstado(EstadoCita.CONFIRMADA).isEmpty());
    }

    @Test
    void buscarPorPrioridadDevuelveSoloLasCitasDeLaPrioridadIndicada() {
        assertEquals(List.of(10L), ids(servicio.buscarPorPrioridad(Prioridad.NORMAL)));
        assertEquals(List.of(11L), ids(servicio.buscarPorPrioridad(Prioridad.ALTA)));
        assertTrue(servicio.buscarPorPrioridad(Prioridad.BAJA).isEmpty());
        assertTrue(servicio.buscarPorPrioridad(Prioridad.URGENTE).isEmpty());
    }

    @Test
    void alConfirmarUnaCambiaElConteoPorEstado() {
        servicio.confirmarCita(10L);
        assertEquals(List.of(11L), ids(servicio.buscarPorEstado(EstadoCita.PROGRAMADA)));
        assertEquals(List.of(10L), ids(servicio.buscarPorEstado(EstadoCita.CONFIRMADA)));
    }

    @Test
    void citasDeAgendaDevuelveSoloLasDeEsaAgendaOrdenadasPorId() {
        AgendaMedica otra = agenda(2L, hoy.plusDays(2));
        agregarCita(otra, 5L, 8);
        servicio = servicio(List.of(otra, agenda));
        assertEquals(List.of(10L, 11L), ids(servicio.citasDeAgenda(1L)));
    }

    @Test
    void cuposDisponiblesDescuentaLasAgendadas() {
        assertEquals(4, servicio.cuposDisponibles(1L));
    }

    @Test
    void agendaInexistenteLanzaExcepcionEnAmbasConsultas() {
        assertThrows(CitaNoEncontradaException.class, () -> servicio.citasDeAgenda(999L));
        assertThrows(CitaNoEncontradaException.class, () -> servicio.cuposDisponibles(999L));
    }

    @Test
    void proximasCitasTraeLasDeManana() {
        assertEquals(List.of(10L, 11L), ids(servicio.proximasCitas()));
    }

    @Test
    void proximasCitasIncluyeHoyExcluyePasadasYOrdenaPorFechaYHora() {
        AgendaMedica ayer = agenda(2L, hoy.minusDays(1));
        AgendaMedica actual = agenda(3L, hoy);
        agregarCita(ayer, 1L, 8);
        agregarCita(actual, 30L, 8);
        agregarCita(actual, 20L, 10);
        servicio = servicio(List.of(agenda, ayer, actual));
        assertEquals(List.of(30L, 20L, 10L, 11L), ids(servicio.proximasCitas()));
    }

    @Test
    void proximasCitasExcluyeEstadosFinalesYConservaLosActivosOrdenados() {
        agregarCita(agenda, 12L, 10);
        agregarCita(agenda, 13L, 11);
        agenda.agendarPresencial(14L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(9, 30), LocalTime.of(10, 0), "Consultorio 1", null);
        agenda.agendarPresencial(15L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(8, 30), LocalTime.of(9, 0), "Consultorio 1", null);
        servicio = servicio(List.of(agenda));
        servicio.cancelarCita(10L);
        servicio.confirmarCita(11L);
        servicio.marcarNoAsistio(11L);
        servicio.confirmarCita(12L);
        servicio.atenderCita(12L);
        servicio.confirmarCita(14L);
        servicio.reprogramarCita(15L, hoy.plusDays(2), LocalTime.of(8, 0), LocalTime.of(8, 30));

        assertEquals(List.of(14L, 13L, 15L), ids(servicio.proximasCitas()));
    }

    @Test
    void agendaVaciaYRepositorioVacioDevuelvenResultadosVacios() {
        servicio = servicio(List.of(agenda(2L, hoy)));
        assertTrue(servicio.citasDeAgenda(2L).isEmpty());
        assertEquals(6, servicio.cuposDisponibles(2L));
        assertTrue(servicio.buscarPorEstado(EstadoCita.PROGRAMADA).isEmpty());
        assertTrue(servicio.buscarPorPrioridad(Prioridad.NORMAL).isEmpty());
        assertTrue(servicio.proximasCitas().isEmpty());
    }

    @Test
    void consultasNoCambianEstadoCuposNiNotifican() {
        servicio.buscarPorEstado(EstadoCita.PROGRAMADA);
        servicio.buscarPorPrioridad(Prioridad.ALTA);
        servicio.citasDeAgenda(1L);
        servicio.cuposDisponibles(1L);
        servicio.proximasCitas();
        assertEquals(0, notificaciones);
        assertEquals(4, agenda.cuposDisponibles());
        assertTrue(agenda.getCitas().stream().allMatch(c -> c.getEstadoCita() == EstadoCita.PROGRAMADA));
    }

    private AgendaMedica agenda(Long id, LocalDate fecha) {
        RangoHorario jornada = new RangoHorario(id, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
        return new AgendaMedica(id, null, null, fecha, jornada, 6, true);
    }

    private void agregarCita(AgendaMedica destino, Long id, int hora) {
        destino.agendarPresencial(id, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(hora, 0), LocalTime.of(hora, 30), "Consultorio 1", null);
    }

    private OperacionesCita servicio(List<AgendaMedica> agendas) {
        RepositorioMemoria repositorio = new RepositorioMemoria(agendas);
        return new CitaServicio(repositorio, repositorio,
                List.of((cita, estado) -> notificaciones++));
    }

    private List<Long> ids(List<Cita> citas) {
        return citas.stream().map(Cita::getId).toList();
    }
}
