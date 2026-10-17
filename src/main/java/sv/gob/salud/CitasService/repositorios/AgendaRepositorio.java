package sv.gob.salud.CitasService.repositorios;

import java.util.List;
import java.util.Optional;

import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;

public interface AgendaRepositorio {
    List<AgendaMedica> buscarAgendas();

    Optional<AgendaMedica> buscarAgendaPorId(Long id);
}