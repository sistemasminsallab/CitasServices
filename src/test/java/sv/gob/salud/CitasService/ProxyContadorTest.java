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

/** Verifica que el contador distinga los métodos y cuente también los intentos fallidos. */
class ProxyContadorTest {

    private CitaServicio real;

    @BeforeEach
    void preparar() {
        RangoHorario jornada = new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
        AgendaMedica agenda = new AgendaMedica(1L, null, null, LocalDate.now().plusDays(1), jornada, 6, true);
        agenda.agendarPresencial(10L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(8, 0), LocalTime.of(8, 30), "Consultorio 1", null);
        RepositorioMemoria repositorio = new RepositorioMemoria(List.of(agenda));
        real = new CitaServicio(repositorio, repositorio, List.of());
    }

    @Test
    void cuentaLasLlamadasPorMetodo() {
        Map<String, Integer> conteo = new HashMap<>();
        OperacionesCita contado = ProxyCita.conContador(real, conteo);

        contado.listarCitas();
        contado.listarCitas();
        contado.obtenerCita(10L);

        // Una operación fallida también cuenta y conserva su excepción original de negocio.
        assertThrows(IllegalStateException.class, () -> contado.atenderCita(10L));

        // La inspección del proxy no debe agregar entradas al mapa.
        contado.toString();
        contado.hashCode();
        contado.equals(real);

        assertEquals(Map.of("listarCitas", 2, "obtenerCita", 1, "atenderCita", 1), conteo);
    }
}
