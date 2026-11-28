package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.modelos.entidades.RangoHorario;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;

/** RangoHorario como objeto de valor: inmutable, validado e igual por contenido. */
class RangoHorarioTest {

    private RangoHorario manana() {
        return new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
    }

    @Test
    void dosJornadasConLosMismosDatosSonIgualesPeroSonDosObjetos() {
        RangoHorario a = manana();
        RangoHorario b = manana();
        assertNotSame(a, b);                       // dos recuadros en el Heap
        assertEquals(a, b);                        // el record compara por contenido
        assertEquals(a.hashCode(), b.hashCode());
        assertTrue(Set.of(a).contains(b));         // sirve como clave en colecciones hash
    }

    @Test
    void rechazaUnaJornadaInvertidaOVacia() {
        assertThrows(IllegalArgumentException.class,
                () -> new RangoHorario(1L, LocalTime.of(12, 0), LocalTime.of(8, 0), Modalidad.VIRTUAL));
        assertThrows(IllegalArgumentException.class,
                () -> new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(8, 0), Modalidad.VIRTUAL));
    }

    @Test
    void rechazaDatosNulos() {
        assertThrows(NullPointerException.class,
                () -> new RangoHorario(1L, null, LocalTime.of(12, 0), Modalidad.VIRTUAL));
        assertThrows(NullPointerException.class,
                () -> new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), null));
    }

    @Test
    void calculaDuracionYContieneHoras() {
        RangoHorario jornada = manana();
        assertEquals(240, jornada.duracionMinutos());
        assertTrue(jornada.contiene(LocalTime.of(8, 0)));
        assertTrue(jornada.contiene(LocalTime.of(12, 0)));
        assertFalse(jornada.contiene(LocalTime.of(12, 1)));
        assertFalse(jornada.contiene(null));
    }

    @Test
    void detectaSolapamiento() {
        RangoHorario tarde = new RangoHorario(2L, LocalTime.of(11, 0), LocalTime.of(15, 0), Modalidad.PRESENCIAL);
        RangoHorario noche = new RangoHorario(3L, LocalTime.of(12, 0), LocalTime.of(18, 0), Modalidad.PRESENCIAL);
        assertTrue(manana().seSolapaCon(tarde));    // 11:00 a 12:00 en común
        assertFalse(manana().seSolapaCon(noche));   // se tocan en las 12:00 pero no se pisan
        assertFalse(manana().seSolapaCon(null));
    }
}
