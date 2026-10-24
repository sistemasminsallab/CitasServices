package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.entidades.CitaDomiciliaria;
import sv.gob.salud.CitasService.modelos.entidades.CitaPresencial;
import sv.gob.salud.CitasService.modelos.entidades.CitaVirtual;
import sv.gob.salud.CitasService.modelos.entidades.Establecimiento;
import sv.gob.salud.CitasService.modelos.entidades.Expediente;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;

class PrototypeCitaTest {

    @Test
    void clonarCitaPresencial() {
        CitaPresencial original = new CitaPresencial(1L, null, new Expediente(), 1,
                EstadoCita.PROGRAMADA, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ, null,
                LocalDate.now(), LocalTime.of(8, 0), LocalTime.of(8, 30),
                "Consultorio 101", new Establecimiento(1L, "H001", "Hospital", "San Salvador"), "Sin indicaciones");

        Cita clon = original.clonar();

        assertNotSame(original, clon);
        assertInstanceOf(CitaPresencial.class, clon);
        assertEquals("Consultorio 101", ((CitaPresencial) clon).getConsultorio());
    }

    @Test
    void clonarCitaVirtual() {
        CitaVirtual original = new CitaVirtual(2L, null, new Expediente(), 2,
                EstadoCita.PROGRAMADA, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ, null,
                LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(9, 30),
                "https://meet.example.com/cita", "Meet");

        Cita clon = original.clonar();

        assertNotSame(original, clon);
        assertInstanceOf(CitaVirtual.class, clon);
        assertEquals("https://meet.example.com/cita", ((CitaVirtual) clon).getUrl());
    }

    @Test
    void clonarCitaDomiciliaria() {
        CitaDomiciliaria original = new CitaDomiciliaria(3L, null, new Expediente(), 3,
                EstadoCita.PROGRAMADA, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ, null,
                LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(10, 30),
                "Colonia Escalón", "Frente a supermercado");

        Cita clon = original.clonar();

        assertNotSame(original, clon);
        assertInstanceOf(CitaDomiciliaria.class, clon);
        assertEquals("Colonia Escalón", ((CitaDomiciliaria) clon).getDireccionVisita());
    }
}
