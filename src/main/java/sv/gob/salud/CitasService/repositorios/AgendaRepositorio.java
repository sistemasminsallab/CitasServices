package sv.gob.salud.CitasService.repositorios;

import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;

import java.util.List;
import java.util.Optional;

public interface AgendaRepositorio {
    List<AgendaMedica> buscarAgendas();
    Optional<AgendaMedica> buscarAgendaPorId(Long id);
}
