package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.modelos.entidades.CitaPresencial;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;

public class PrototypeCitaTest {

    @Test
    void verificaClonacionDeCitaPresencial() {
        // Arrange: Crear la cita original presencial
        LocalDate fechaOriginal = LocalDate.now().plusDays(1);
        CitaPresencial original = new CitaPresencial.Builder()
                .id(1L)
                .orden(1)
                .estadoCita(EstadoCita.PROGRAMADA)
                .prioridad(Prioridad.NORMAL)
                .tipo(TipoCita.PRIMERA_VEZ)
                .fechaCita(fechaOriginal)
                .horaInicio(LocalTime.of(8, 0))
                .horaFin(LocalTime.of(9, 0))
                .consultorio("C-203")
                .build();

        LocalDate nuevaFecha = LocalDate.now().plusDays(5);
        LocalTime nuevaHoraInicio = LocalTime.of(10, 0);
        LocalTime nuevaHoraFin = LocalTime.of(11, 0);

        // Act: Clonar la cita con el nuevo ID, fecha y horarios
        CitaPresencial clon = original.clonarCon(2L, nuevaFecha, nuevaHoraInicio, nuevaHoraFin);

        // Assert: Validaciones requeridas
        assertSame(original, clon.getCitaOrigen(), "El origen de la cita clonada debe ser la cita original");
        assertEquals("C-203", clon.getConsultorio(), "El consultorio debe ser copiado correctamente del original");
        assertEquals(nuevaFecha, clon.getFechaCita(), "La fecha de la cita debe actualizarse a la nueva fecha");
    }
}