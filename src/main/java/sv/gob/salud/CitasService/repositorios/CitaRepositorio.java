package sv.gob.salud.CitasService.repositorios;

import java.util.List;
import java.util.Optional;

import sv.gob.salud.CitasService.modelos.entidades.Cita;

/** Contrato de acceso a citas. El servicio depende de esto, no de cómo se guardan (DIP). */
public interface CitaRepositorio {
    List<Cita> buscarCitas();
    Optional<Cita> buscarCitaPorId(Long id);
    Long siguienteId();
}
