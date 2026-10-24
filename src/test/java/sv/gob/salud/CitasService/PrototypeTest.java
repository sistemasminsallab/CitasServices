package sv.gob.salud.CitasService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.modelos.entidades.CitaPresencial;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;

class PrototypeCitaTest {

    // Cita presencial base; id=1 y orden=1 respetan las invariantes (deben ser > 0).
    private CitaPresencial original() {
        return new CitaPresencial.Builder()
                .id(1L).agendaMedica(null).expediente(null).orden(1)
                .estadoCita(EstadoCita.PROGRAMADA).prioridad(Prioridad.NORMAL)
                .tipo(TipoCita.PRIMERA_VEZ).citaOrigen(null)
                .fechaCita(LocalDate.now().plusDays(1))
                .horaInicio(LocalTime.of(8, 0)).horaFin(LocalTime.of(9, 0))
                .consultorio("C-203").establecimiento(null).indicaciones(null)
                .build();
    }

    @Test
    void clonarEnlazaOriginalYCambiaSoloFechaEId() {
        CitaPresencial original = original();
        LocalDate nuevaFecha = original.getFechaCita().plusDays(7);

        CitaPresencial copia = original.clonarCon(
                2L, nuevaFecha, LocalTime.of(10, 0), LocalTime.of(11, 0));

        assertSame(original, copia.getCitaOrigen());        // enlaza el original
        assertEquals("C-203", copia.getConsultorio());      // campo propio copiado
        assertEquals(nuevaFecha, copia.getFechaCita());     // fecha cambiada
        assertNotEquals(original.getId(), copia.getId());   // es una cita nueva
    }
}