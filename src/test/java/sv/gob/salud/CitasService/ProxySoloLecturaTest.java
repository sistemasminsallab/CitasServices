package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import sv.gob.salud.CitasService.registro.ProxyCita;
import sv.gob.salud.CitasService.repositorios.RepositorioMemoria;
import sv.gob.salud.CitasService.servicios.CitaServicio;
import sv.gob.salud.CitasService.servicios.OperacionesCita;

/**
 * Proxy de protección: sobre la misma interfaz {@link OperacionesCita}, el modo solo lectura deja
 * pasar las consultas y bloquea cualquier operación que modifique el estado de las citas.
 */
class ProxySoloLecturaTest {

    private OperacionesCita servicioSoloLectura;

    @BeforeEach
    void preparar() {
        RangoHorario jornada = new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
        AgendaMedica agenda = new AgendaMedica(1L, null, null, LocalDate.now().plusDays(1), jornada, 6, true);
        agenda.agendarPresencial(10L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(8, 0), LocalTime.of(8, 30), "Consultorio 1", null);
        RepositorioMemoria repositorio = new RepositorioMemoria(List.of(agenda));
        CitaServicio servicioReal = new CitaServicio(repositorio, repositorio, List.of());
        servicioSoloLectura = ProxyCita.soloLectura(servicioReal);
    }

    @Test
    void permiteLasOperacionesDeConsultaYDelegaEnElServicioReal() {
        assertEquals(1, servicioSoloLectura.listarCitas().size());
        assertEquals(1, servicioSoloLectura.listarAgendas().size());
        Cita cita = servicioSoloLectura.obtenerCita(10L);
        assertEquals(EstadoCita.PROGRAMADA, cita.getEstadoCita());
    }

    @Test
    void bloqueaLasOperacionesQueModificanElEstado() {
        assertThrows(IllegalStateException.class, () -> servicioSoloLectura.confirmarCita(10L));
        assertThrows(IllegalStateException.class, () -> servicioSoloLectura.cancelarCita(10L));
        assertThrows(IllegalStateException.class, () -> servicioSoloLectura.atenderCita(10L));
        assertThrows(IllegalStateException.class, () -> servicioSoloLectura.marcarNoAsistio(10L));
        assertThrows(IllegalStateException.class,
                () -> servicioSoloLectura.reprogramarCita(10L, LocalDate.now().plusDays(2),
                        LocalTime.of(9, 0), LocalTime.of(9, 30)));
        // La cita no cambió: el proxy bloqueó antes de llegar al servicio real.
        assertEquals(EstadoCita.PROGRAMADA, servicioSoloLectura.obtenerCita(10L).getEstadoCita());
    }
}
