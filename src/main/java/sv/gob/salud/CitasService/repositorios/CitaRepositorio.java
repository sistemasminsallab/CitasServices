package sv.gob.salud.CitasService.repositorios;

import sv.gob.salud.CitasService.modelos.entidades.Cita;

import java.util.List;
import java.util.Optional;

public interface CitaRepositorio {
    List<Cita> buscarCitas();

    Optional<Cita> buscarCitaPorId(Long id);

    Long siguienteId();
}
