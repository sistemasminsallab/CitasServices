package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Proxy contador: sobre la misma interfaz {@link OperacionesCita}, cuenta cuántas veces se invoca
 * cada operación, delegando siempre en el servicio real.
 */
class ProxyContadorTest {

    private Map<String, Integer> conteo;
    private OperacionesCita servicioConContador;

    @BeforeEach
    void preparar() {
        RangoHorario jornada = new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
        AgendaMedica agenda = new AgendaMedica(1L, null, null, LocalDate.now().plusDays(1), jornada, 6, true);
        agenda.agendarPresencial(10L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(8, 0), LocalTime.of(8, 30), "Consultorio 1", null);
        RepositorioMemoria repositorio = new RepositorioMemoria(List.of(agenda));
        CitaServicio servicioReal = new CitaServicio(repositorio, repositorio, List.of());
        conteo = new HashMap<>();
        servicioConContador = ProxyCita.conContador(servicioReal, conteo);
    }

    @Test
    void cuentaCadaInvocacionPorMetodo() {
        servicioConContador.listarCitas();
        servicioConContador.listarCitas();
        servicioConContador.obtenerCita(10L);

        assertEquals(2, conteo.get("listarCitas"));
        assertEquals(1, conteo.get("obtenerCita"));
        assertEquals(3, conteo.values().stream().mapToInt(Integer::intValue).sum());
    }

    @Test
    void cuentaTambienLasInvocacionesQueTerminanEnExcepcion() {
        // PROGRAMADA -> ATENDIDA es ilegal, pero la llamada igual se cuenta antes de delegar.
        assertThrows(IllegalStateException.class, () -> servicioConContador.atenderCita(10L));

        assertEquals(1, conteo.get("atenderCita"));
    }
}
