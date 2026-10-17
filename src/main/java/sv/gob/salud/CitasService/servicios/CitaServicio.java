package sv.gob.salud.CitasService.servicios;

import org.springframework.stereotype.Service;
import sv.gob.salud.CitasService.dto.AgendarCitaSolicitud;
import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.modelos.excepciones.CitaNoEncontradaException;
import sv.gob.salud.CitasService.repositorios.AgendaRepositorio;
import sv.gob.salud.CitasService.repositorios.CitaRepositorio;

import java.util.List;

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

    public Cita agendar(AgendarCitaSolicitud solicitud) {
        AgendaMedica agenda = agendas.buscarAgendaPorId(solicitud.agendaId())
                .orElseThrow(() -> new CitaNoEncontradaException("No existe la agenda " + solicitud.agendaId()));
        Long id = citas.siguienteId();
        if (solicitud.modalidad() == Modalidad.VIRTUAL) {
            return agenda.agendarVirtual(id, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                    solicitud.horaInicio(), solicitud.horaFin(), "https://meet.minsal.gob.sv/" + id, "Meet");
        }
        return agenda.agendarPresencial(id, null, Prioridad.NORMAL, TipoCita.PRIMERA_VEZ,
                solicitud.horaInicio(), solicitud.horaFin(), "Consultorio 1", null);
    }
}
