package sv.gob.salud.CitasService.datos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.Empleado;
import sv.gob.salud.CitasService.modelos.entidades.Establecimiento;
import sv.gob.salud.CitasService.modelos.entidades.Especialidad;
import sv.gob.salud.CitasService.modelos.entidades.RangoHorario;
import sv.gob.salud.CitasService.modelos.enums.Genero;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;

/** Datos de ejemplo para mostrar algo sin base de datos. Se elimina cuando llegue la BD. */
@Configuration
public class DatosDemo {

    @Bean
    public List<AgendaMedica> agendasDemo() {
        Empleado medico = new Empleado("00000001-8", "Ana", "Martínez", Genero.FEMENINO,
                LocalDate.of(1985, 3, 12), "7000-0000", "ana.martinez@salud.gob.sv", "JVPM-1234");
        Establecimiento unidad = new Establecimiento(1L, "UMS-01", "Unidad Médica San Salvador", "Centro, San Salvador");
        RangoHorario jornada = new RangoHorario(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), Modalidad.PRESENCIAL);
        LocalDate fecha = LocalDate.now().plusDays(1);

        AgendaMedica agenda = new AgendaMedica(1L, medico, new Especialidad(), fecha, jornada, 6, true);
        agenda.agendarPresencial(1L, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                LocalTime.of(8, 0), LocalTime.of(8, 30), "Consultorio 2", unidad);
        agenda.agendarVirtual(2L, null, Prioridad.ALTA, TipoCita.SUBSECUENTE,
                LocalTime.of(9, 0), LocalTime.of(9, 30), "https://meet.minsal.gob.sv/abc", "Meet");
        return List.of(agenda);
    }
}