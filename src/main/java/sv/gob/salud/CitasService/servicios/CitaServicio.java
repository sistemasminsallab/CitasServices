package sv.gob.salud.CitasService.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.excepciones.CitaNoEncontradaException;
import sv.gob.salud.CitasService.modelos.fabricas.CitaFactory;
import sv.gob.salud.CitasService.modelos.fabricas.DatosCita;
import sv.gob.salud.CitasService.repositorios.AgendaRepositorio;
import sv.gob.salud.CitasService.repositorios.CitaRepositorio;

/** Fabricación pura: orquesta casos de uso; las reglas de negocio siguen en el dominio. */
@Service
public class CitaServicio {
    private final CitaRepositorio citas;
    private final AgendaRepositorio agendas;

    public CitaServicio(CitaRepositorio citas, AgendaRepositorio agendas) {
        this.citas = citas;
        this.agendas = agendas;
    }

    public List<Cita> listarCitas() {
        return citas.buscarCitas();
    }

    public Cita obtenerCita(Long id) {
        return citas.buscarCitaPorId(id)
                .orElseThrow(() -> new CitaNoEncontradaException("No existe la cita " + id));
    }

    public List<AgendaMedica> listarAgendas() {
        return agendas.buscarAgendas();
    }

    public Cita agendar(Modalidad modalidad, DatosCita datos) {
        return CitaFactory.crear(modalidad, datos);
    }
}
