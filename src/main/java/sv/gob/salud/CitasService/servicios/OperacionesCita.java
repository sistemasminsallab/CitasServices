package sv.gob.salud.CitasService.servicios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import sv.gob.salud.CitasService.dto.AgendarCitaSolicitud;
import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.Cita;

/**
 * Contrato de los casos de uso de citas.
 *
 * <p>Existe para que un Proxy dinámico ({@link java.lang.reflect.Proxy}) pueda envolver al servicio
 * real: los proxies dinámicos del JDK se construyen sobre una interfaz, no sobre una clase concreta.
 * Extraer esta interfaz también desacopla a los clientes de la implementación (DIP), el mismo
 * contrato sobre el que Spring creará sus proxies de AOP en el Módulo 6.
 */
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
