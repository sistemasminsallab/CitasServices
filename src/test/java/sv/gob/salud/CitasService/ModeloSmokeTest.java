package sv.gob.salud.CitasService;

import org.junit.jupiter.api.Test;
import sv.gob.salud.CitasService.modelos.entidades.CitaDomiciliaria;
import sv.gob.salud.CitasService.modelos.entidades.Genero;
import sv.gob.salud.CitasService.modelos.entidades.Paciente;
import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.modelos.excepciones.DatosPersonaInvalidosException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ModeloSmokeTest {

    private Paciente paciente(String dui, LocalDate nacimiento){
        return new Paciente(dui, "Luis", "Perez", Genero.MASCULINO, nacimiento, null, "1234-24");
    }

    private CitaDomiciliaria visita(String direccion, String referencia){
        return new CitaDomiciliaria(1L, null, null, 1, EstadoCita.PROGRAMADA, Prioridad.NORMAL,
                TipoCita.PRIMERA_VEZ, null, LocalDate.now().plusDays(1),
                LocalTime.of(8, 0), LocalTime.of(9,0), direccion, referencia);
    }

    @Test
    void duiValidadoSeAcepta(){
        assertEquals("00000001-8", paciente("00000001-8", LocalDate.of(1990, 1, 1)).getDocumentoIdentidad());
    }

    @Test
    void duiInvalidoSeRechaza(){
        assertThrows(DatosPersonaInvalidosException.class, () -> paciente("00000001-9", LocalDate.of(1990, 1, 1)));
    }

    @Test
    void edadCalculaEnAnios(){
        assertEquals(30, paciente("00000001-8", LocalDate.now().minusYears(30)).edad());
    }

    @Test
    void contactoRegistradoSoloSiHayTelefonoCorreo(){
        assertTrue(paciente("00000001-8", LocalDate.of(1990, 1, 1)).tieneContactoRegistrado());
    }

    @Test
    void ubicacionDomiciliariaConYSinReferencia(){
        assertEquals("Col. Escalon - Referencia: casa azul", visita("Col. Escalon", "casa azul").obtenerUbicacionCompleta());
        assertEquals("Col. Escalon", visita("Col. Escalon", " ").obtenerUbicacionCompleta());
        assertFalse(visita("Col. Escalon", null).tieneReferenciaUbicacion());
    }

    @Test
    void direccionObligatoria(){
        assertThrows(IllegalArgumentException.class, () -> visita(" ", null));
    }

    @Test
    void citaCanceladaNoSePuedeReprogramar(){
        CitaDomiciliaria cita = visita("Col. Escalon", null);
        cita.cambiarEstadoCita(EstadoCita.CANCELADA);
        assertThrows(IllegalStateException.class,
                () -> cita.reprogramarCita(LocalDate.now().plusDays(2), LocalTime.of(8, 0), LocalTime.of(9,0)));
    }







}
