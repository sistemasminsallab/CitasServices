package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
 * Observer: al confirmar una cita, el servicio notifica a todos los observadores
 * sin conocer sus clases concretas. Agregar un observador no cambia el servicio (OCP).
 */
class ObserverConfirmacionTest {

    /** Observador de prueba: anota si fue notificado y con qué cita. */
    private static class ObservadorSpy implements ObservadorCita {
        boolean notificado = false;
        Cita recibida;

        @Override
        public void alConfirmar(Cita cita) {
            notificado = true;
            recibida = cita;
        }
    }

    private RepositorioMemoria repositorio;

    @BeforeEach
    void preparar() {
        RangoHorario jornada = new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
        AgendaMedica agenda = new AgendaMedica(1L, null, null, LocalDate.now().plusDays(1), jornada, 6, true);
        agenda.agendarPresencial(10L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(8, 0), LocalTime.of(8, 30), "Consultorio 1", null);
        repositorio = new RepositorioMemoria(List.of(agenda));
    }

    @Test
    void confirmarCitaCambiaElEstadoANotificarALosObservadores() {
        ObservadorSpy correo = new ObservadorSpy();
        ObservadorSpy auditoria = new ObservadorSpy();
        CitaServicio servicio = new CitaServicio(repositorio, repositorio, List.of(correo, auditoria));

        Cita confirmada = servicio.confirmarCita(10L);

        assertEquals(EstadoCita.CONFIRMADA, confirmada.getEstadoCita());
        assertTrue(correo.notificado, "El observador de correo debió ser notificado");
        assertTrue(auditoria.notificado, "El observador de auditoría debió ser notificado");
        assertEquals(10L, correo.recibida.getId());
    }

    @Test
    void sinObservadoresLaConfirmacionSigueFuncionando() {
        CitaServicio servicio = new CitaServicio(repositorio, repositorio, List.of());

        Cita confirmada = servicio.confirmarCita(10L);

        assertEquals(EstadoCita.CONFIRMADA, confirmada.getEstadoCita());
    }
}
