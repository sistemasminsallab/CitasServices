package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
import sv.gob.salud.CitasService.registro.RegistroOperacion;
import sv.gob.salud.CitasService.repositorios.RepositorioMemoria;
import sv.gob.salud.CitasService.servicios.CitaServicio;
import sv.gob.salud.CitasService.servicios.OperacionesCita;

/**
 * Proxy dinámico (base de Spring AOP): un proxy sobre {@link OperacionesCita} registra cada
 * operación (método + duración) sin tocar la lógica de {@link CitaServicio}. El cliente usa la
 * misma interfaz y no nota el proxy; el resultado del negocio es idéntico.
 */
class ProxyRegistroCitaTest {

    private RepositorioMemoria repositorio;
    private CitaServicio servicioReal;
    private List<RegistroOperacion> bitacora;
    private OperacionesCita servicioConRegistro;

    @BeforeEach
    void preparar() {
        RangoHorario jornada = new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
        AgendaMedica agenda = new AgendaMedica(1L, null, null, LocalDate.now().plusDays(1), jornada, 6, true);
        agenda.agendarPresencial(10L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(8, 0), LocalTime.of(8, 30), "Consultorio 1", null);
        repositorio = new RepositorioMemoria(List.of(agenda));
        servicioReal = new CitaServicio(repositorio, repositorio, List.of());
        bitacora = new ArrayList<>();
        servicioConRegistro = ProxyCita.conRegistro(servicioReal, bitacora::add);
    }

    @Test
    void elProxyDelegaEnElServicioRealYRegistraLaOperacion() {
        Cita confirmada = servicioConRegistro.confirmarCita(10L);

        // Delegó: el resultado del negocio es el mismo que llamando al servicio real.
        assertEquals(EstadoCita.CONFIRMADA, confirmada.getEstadoCita());
        // Registró: una línea de bitácora para el método interceptado.
        assertEquals(1, bitacora.size());
        assertEquals("confirmarCita", bitacora.get(0).metodo());
        assertTrue(bitacora.get(0).duracionNanos() >= 0, "La duración medida no puede ser negativa");
    }

    @Test
    void elProxyPropagaLasExcepcionesDelNegocioYAunAsiRegistra() {
        // PROGRAMADA -> ATENDIDA es ilegal; la excepción real debe llegar al cliente sin envolver.
        assertThrows(IllegalStateException.class, () -> servicioConRegistro.atenderCita(10L));
        // Aun fallando, el aspecto de registro corrió (bloque finally).
        assertEquals(1, bitacora.size());
        assertEquals("atenderCita", bitacora.get(0).metodo());
    }
}
