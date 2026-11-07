package sv.gob.salud.CitasService.servicios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import sv.gob.salud.CitasService.dto.AgendarCitaSolicitud;
import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.Cita;

public interface OperacionesCita {
     List<Cita> listarCitas();

    Cita obtenerCita(Long id);

    List<AgendaMedica> listarAgendas();

    Cita agendar(AgendarCitaSolicitud solicitud);

    Cita confirmarCita(Long id);

    Cita cancelarCita(Long id);

    Cita atenderCita(Long id);

    Cita marcarNoAsistio(Long id);

    Cita reprogramarCita(Long id, LocalDate fecha, LocalTime inicio, LocalTime fin);
}
