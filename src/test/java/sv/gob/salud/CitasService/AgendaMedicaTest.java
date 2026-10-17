package sv.gob.salud.CitasService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.RangoHorario;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.modelos.excepciones.CitaNoDisponibleException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AgendaMedicaTest {
    private AgendaMedica agenda;

    @BeforeEach
    void preparar() {
        RangoHorario jornada = new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
        agenda = new AgendaMedica(1L, null, null, LocalDate.now().plusDays(1), jornada, 2, true);
    }

    private void agendar(long id, int horaInicio, int horaFin) {
        agenda.agendarVirtual(id, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(horaInicio, 0), LocalTime.of(horaFin, 0), "https://meet/x", "Meet");
    }

    @Test
    void agendarDescuentaCupo() {
        agendar(1, 8, 9);
        assertEquals(1, agenda.cuposDisponibles());
    }

    @Test
    void noSeAgendaUnaCitaSolapada() {
        agendar(1, 8, 10);
        assertThrows(CitaNoDisponibleException.class, () -> agendar(2, 9, 11));
    }

    @Test
    void noSeAgendaFueraDelHorario() {
        assertThrows(CitaNoDisponibleException.class, () -> agendar(1, 13, 14));
    }

    @Test
    void noSeAgendaSinCupo() {
        agendar(1, 8, 9);
        agendar(2, 9, 10);
        assertThrows(CitaNoDisponibleException.class, () -> agendar(3, 10, 11));
    }
}
