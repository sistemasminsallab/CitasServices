package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.entidades.CitaPresencial;
import sv.gob.salud.CitasService.modelos.entidades.CitaVirtual;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.modelos.fabricas.CitaFactory;
import sv.gob.salud.CitasService.modelos.fabricas.DatosCita;

class FactoryCitaTest {

    // Datos base comunes; cada test llena solo el campo propio de su modalidad.
    // id=1 y orden=1 respetan las invariantes del constructor de Cita (deben ser > 0).
    private DatosCita datos(String consultorio, String url, String plataforma) {
        return new DatosCita(
                1L, null, null, 1,                                   // id, agendaMedica, expediente, orden
                EstadoCita.PROGRAMADA, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ, null, // estado, prioridad, tipo, citaOrigen
                LocalDate.now().plusDays(1), LocalTime.of(8, 0), LocalTime.of(9, 0), // fecha, horaInicio, horaFin
                consultorio, null, null,                             // consultorio, establecimiento, indicaciones
                null, null,                                          // direccionVisita, referenciaUbicacion
                url, plataforma);                                    // url, plataforma
    }

    @Test
    void creaCitaPresencialSegunLaModalidad() {
        Cita cita = CitaFactory.crear(Modalidad.PRESENCIAL, datos("C-203", null, null));

        CitaPresencial presencial = assertInstanceOf(CitaPresencial.class, cita);
        assertEquals("C-203", presencial.getConsultorio());
    }

    @Test
    void creaCitaVirtualSegunLaModalidad() {
        Cita cita = CitaFactory.crear(Modalidad.VIRTUAL, datos(null, "https://meet.example/abc", "Zoom"));

        CitaVirtual virtual = assertInstanceOf(CitaVirtual.class, cita);
        assertEquals("https://meet.example/abc", virtual.getUrl());
    }
}
