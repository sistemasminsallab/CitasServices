package sv.gob.salud.CitasService.repositorios;

import java.util.List;
import java.util.Optional;

import sv.gob.salud.CitasService.modelos.entidades.Cita;

public interface CitaRepositorio {
    List<Cita> buscarCitas();

    Optional<Cita> buscarCitaPorId(Long id);

    Long siguienteId();
}
