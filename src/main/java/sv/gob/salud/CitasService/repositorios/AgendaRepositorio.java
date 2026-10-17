package sv.gob.salud.CitasService.repositorios;

import java.util.List;
import java.util.Optional;

import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;

public interface AgendaRepositorio {
    List<AgendaMedica> buscarAgendas();

    Optional<AgendaMedica> buscarAgendaPorId(Long id);

    // Cuantas clases hay que editar para agregar la modalidad TELEFONICA? Agregar al enum Modalidad la linea TELEFONICA y luego crear la clase que extienda de Cita, por ejemplo CitaTelefonica
    // Que clase Conoce mas clases? CitaMedica conoce a CitaPresencial, CitaDomiciliaria y CitaVirtual. CitaVirtual conoce a CitaMedica. CitaPresencial conoce a CitaMedica. CitaDomiciliaria conoce a CitaMedica. En el caso de CitaMedica conoce a las otras por ser la creadora de las otras clases. 
}
