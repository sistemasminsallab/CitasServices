package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.entidades.CitaPresencial;
import sv.gob.salud.CitasService.modelos.entidades.RangoHorario;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.modelos.excepciones.CitaNoDisponibleException;

/** Una cita se identifica por su id: igualdad por contenido y sin ids repetidos en la agenda. */
class IgualdadCitaTest {

    private CitaPresencial citaConId(Long id) {
        return new CitaPresencial.Builder()
                .id(id).agendaMedica(null).expediente(null).orden(1)
                .estadoCita(EstadoCita.PROGRAMADA).prioridad(Prioridad.NORMAL)
                .tipo(TipoCita.PRIMERA_VEZ).citaOrigen(null)
                .fechaCita(LocalDate.now().plusDays(1))
                .horaInicio(LocalTime.of(8, 0)).horaFin(LocalTime.of(9, 0))
                .consultorio("C-203").establecimiento(null).indicaciones(null)
                .build();
    }

    @Test
    void dosCitasConElMismoIdSonIgualesPeroSonDosObjetos() {
        Cita a = citaConId(1L);
        Cita b = citaConId(1L);
        assertNotSame(a, b);                 // dos objetos en el Heap
        assertEquals(a, b);                  // equals por id
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void unSetNoGuardaDosCitasConElMismoId() {
        Set<Cita> conjunto = new HashSet<>(List.of(citaConId(1L)));
        conjunto.add(citaConId(1L));
        assertEquals(1, conjunto.size());
    }

    @Test
    void laAgendaRechazaUnIdRepetidoEnOtroHorario() {
        RangoHorario jornada = new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
        AgendaMedica agenda = new AgendaMedica(1L, null, null, LocalDate.now().plusDays(1), jornada, 6, true);
        agenda.agendarPresencial(10L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(8, 0), LocalTime.of(8, 30), "C-1", null);
        assertThrows(CitaNoDisponibleException.class, () -> agenda.agendarPresencial(10L, null, Prioridad.NORMAL,
                TipoCita.PRIMERA_VEZ, LocalTime.of(9, 0), LocalTime.of(9, 30), "C-2", null));
        assertEquals(5, agenda.cuposDisponibles());     // solo ocupó un cupo
    }
}
