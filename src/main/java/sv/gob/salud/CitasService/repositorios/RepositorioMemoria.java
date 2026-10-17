package sv.gob.salud.CitasService.repositorios;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.Cita;

/**
 * Implementación sin base de datos: las citas viven dentro de sus agendas.
 * Cuando llegue la BD (M7) se reemplaza esta clase y nada más cambia.
 */
@Repository
public class RepositorioMemoria implements CitaRepositorio, AgendaRepositorio {
    private final List<AgendaMedica> agendas;
    private final AtomicLong secuencia = new AtomicLong(100);

    public RepositorioMemoria(List<AgendaMedica> agendas) {
        this.agendas = agendas;
    }

    @Override
    public List<AgendaMedica> buscarAgendas() {
        return List.copyOf(agendas);
    }

    @Override
    public Optional<AgendaMedica> buscarAgendaPorId(Long id) {
        return agendas.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    @Override
    public List<Cita> buscarCitas() {
        return agendas.stream().flatMap(a -> a.getCitas().stream())
                .sorted((x, y) -> x.getId().compareTo(y.getId())).toList();
    }

    @Override
    public Optional<Cita> buscarCitaPorId(Long id) {
        return buscarCitas().stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    @Override
    public Long siguienteId() {
        return secuencia.incrementAndGet();
    }
}