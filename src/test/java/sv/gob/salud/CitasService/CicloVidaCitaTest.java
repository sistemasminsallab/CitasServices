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
import sv.gob.salud.CitasService.notificaciones.ObservadorCita;
import sv.gob.salud.CitasService.repositorios.RepositorioMemoria;
import sv.gob.salud.CitasService.servicios.CitaServicio;

/**
 * Ciclo de vida de la cita: la máquina de estados (EstadoCita) solo permite transiciones legales,
 * y cada cambio de estado notifica a los observadores (Observer generalizado).
 */
class CicloVidaCitaTest {

    private static class ObservadorSpy implements ObservadorCita {
        EstadoCita ultimoEstado;
        int veces = 0;

        @Override
        public void alCambiarEstado(Cita cita, EstadoCita nuevoEstado) {
            ultimoEstado = nuevoEstado;
            veces++;
        }
    }

    private RepositorioMemoria repositorio;
    private ObservadorSpy spy;
    private CitaServicio servicio;

    @BeforeEach
    void preparar() {
        RangoHorario jornada = new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
        AgendaMedica agenda = new AgendaMedica(1L, null, null, LocalDate.now().plusDays(1), jornada, 6, true);
        agenda.agendarPresencial(10L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(8, 0), LocalTime.of(8, 30), "Consultorio 1", null);
        repositorio = new RepositorioMemoria(List.of(agenda));
        spy = new ObservadorSpy();
        servicio = new CitaServicio(repositorio, repositorio, List.of(spy));
    }

    @Test
    void cancelarDejaLaCitaEnCanceladaYNotifica() {
        Cita cancelada = servicio.cancelarCita(10L);

        assertEquals(EstadoCita.CANCELADA, cancelada.getEstadoCita());
        assertEquals(EstadoCita.CANCELADA, spy.ultimoEstado);
    }

    @Test
    void noSePuedeAtenderUnaCitaSinConfirmar() {
        // La cita nace PROGRAMADA; PROGRAMADA -> ATENDIDA no es una transición legal.
        assertThrows(IllegalStateException.class, () -> servicio.atenderCita(10L));
    }

    @Test
    void flujoProgramadaConfirmadaAtendidaNotificaCadaPaso() {
        servicio.confirmarCita(10L);
        Cita atendida = servicio.atenderCita(10L);

        assertEquals(EstadoCita.ATENDIDA, atendida.getEstadoCita());
        assertEquals(2, spy.veces, "Debió notificar al confirmar y al atender");
    }

    @Test
    void reprogramarCambiaHorarioEstadoYNotifica() {
        LocalDate nuevaFecha = LocalDate.now().plusDays(2);
        Cita reprogramada = servicio.reprogramarCita(10L, nuevaFecha, LocalTime.of(9, 0), LocalTime.of(9, 30));

        assertEquals(EstadoCita.REPROGRAMADA, reprogramada.getEstadoCita());
        assertEquals(nuevaFecha, reprogramada.getFechaCita());
        assertEquals(LocalTime.of(9, 0), reprogramada.getHoraInicio());
        assertEquals(EstadoCita.REPROGRAMADA, spy.ultimoEstado);
    }

    @Test
    void unaCitaCanceladaEsUnEstadoFinalYNoAdmiteMasCambios() {
        servicio.cancelarCita(10L);

        assertTrue(EstadoCita.CANCELADA.esFinal());
        assertThrows(IllegalStateException.class, () -> servicio.confirmarCita(10L));
    }
}   