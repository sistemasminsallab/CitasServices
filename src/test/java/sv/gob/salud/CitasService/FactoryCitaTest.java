package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

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
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.modelos.fabricas.CitaFactory;
import sv.gob.salud.CitasService.modelos.fabricas.DatosCita;

class FactoryCitaTest {

    @Test
    void crearCitaPresencial() {
        CitaFactory factory = new CitaFactory();
        DatosCita datos = new DatosCita();

        datos.setId(1L);
        datos.setOrden(1);
        datos.setEstadoCita(EstadoCita.PROGRAMADA);
        datos.setPrioridad(Prioridad.NORMAL);
        datos.setTipo(TipoCita.PRIMERA_VEZ);
        datos.setFechaCita(LocalDate.now());
        datos.setHoraInicio(LocalTime.of(8, 0));
        datos.setHoraFin(LocalTime.of(8, 30));
        datos.setExpediente(new Expediente());
        datos.setConsultorio("Consultorio 101");
        datos.setEstablecimiento(new Establecimiento(1L, "H001", "Hospital Nacional", "San Salvador"));
        datos.setIndicaciones("Sin indicaciones");

        Cita cita = factory.crearCita(Modalidad.PRESENCIAL, datos);

        assertInstanceOf(CitaPresencial.class, cita);
        assertEquals("Consultorio 101", ((CitaPresencial) cita).getConsultorio());
    }

    @Test
    void crearCitaVirtual() {
        CitaFactory factory = new CitaFactory();
        DatosCita datos = new DatosCita();

        datos.setId(2L);
        datos.setOrden(2);
        datos.setEstadoCita(EstadoCita.PROGRAMADA);
        datos.setPrioridad(Prioridad.NORMAL);
        datos.setTipo(TipoCita.PRIMERA_VEZ);
        datos.setFechaCita(LocalDate.now());
        datos.setHoraInicio(LocalTime.of(9, 0));
        datos.setHoraFin(LocalTime.of(9, 30));
        datos.setExpediente(new Expediente());
        datos.setUrl("https://meet.example.com/cita");
        datos.setPlataforma("Meet");

        Cita cita = factory.crearCita(Modalidad.VIRTUAL, datos);

        assertInstanceOf(CitaVirtual.class, cita);
        assertEquals("https://meet.example.com/cita", ((CitaVirtual) cita).getUrl());
    }

    @Test
    void crearCitaDomiciliaria() {
        CitaFactory factory = new CitaFactory();
        DatosCita datos = new DatosCita();

        datos.setId(3L);
        datos.setOrden(3);
        datos.setEstadoCita(EstadoCita.PROGRAMADA);
        datos.setPrioridad(Prioridad.NORMAL);
        datos.setTipo(TipoCita.PRIMERA_VEZ);
        datos.setFechaCita(LocalDate.now());
        datos.setHoraInicio(LocalTime.of(10, 0));
        datos.setHoraFin(LocalTime.of(10, 30));
        datos.setExpediente(new Expediente());
        datos.setDireccionVisita("Colonia Escalón, San Salvador");
        datos.setReferenciaUbicacion("Frente a supermercado");

        Cita cita = factory.crearCita(Modalidad.DOMICILIARIA, datos);

        assertInstanceOf(CitaDomiciliaria.class, cita);
        assertEquals("Colonia Escalón, San Salvador", ((CitaDomiciliaria) cita).getDireccionVisita());
    }
}
