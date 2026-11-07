package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
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

/** Combina aspectos envolviendo un proxy con otro que expone la misma interfaz. */
class ProxyComposicionTest {

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
    void registraYBloqueaALaVez() {
        List<RegistroOperacion> bitacora = new ArrayList<>();

        // El bloqueo queda dentro del registro: así también se registran los intentos bloqueados.
        OperacionesCita protegido = ProxyCita.soloLectura(real);
        OperacionesCita apilado = ProxyCita.conRegistro(protegido, bitacora::add);

        assertEquals(real.listarCitas(), apilado.listarCitas());
        var error = assertThrows(IllegalStateException.class, () -> apilado.confirmarCita(10L));
        assertEquals("Sistema en modo solo lectura: no se permite 'confirmarCita'.", error.getMessage());

        // El finally del registro se ejecuta incluso cuando el proxy interno rechaza la escritura.
        assertEquals(2, bitacora.size());
        assertEquals("listarCitas", bitacora.get(0).metodo());
        assertEquals("confirmarCita", bitacora.get(1).metodo());
        assertEquals(EstadoCita.PROGRAMADA, real.obtenerCita(10L).getEstadoCita());
    }
}
