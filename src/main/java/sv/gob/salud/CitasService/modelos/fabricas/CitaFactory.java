package sv.gob.salud.CitasService.modelos.fabricas;

import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.entidades.CitaDomiciliaria;
import sv.gob.salud.CitasService.modelos.entidades.CitaPresencial;
import sv.gob.salud.CitasService.modelos.entidades.CitaVirtual;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;

public class CitaFactory {

    public Cita crearCita(Modalidad modalidad, DatosCita datos) {
        if (modalidad == null) {
            throw new IllegalArgumentException("La modalidad no puede ser nula");
        }
        if (datos == null) {
            throw new IllegalArgumentException("Los datos de la cita no pueden ser nulos");
        }

        return switch (modalidad) {
            case PRESENCIAL -> new CitaPresencial(
                    datos.getId(),
                    datos.getAgendaMedica(),
                    datos.getExpediente(),
                    datos.getOrden(),
                    datos.getEstadoCita(),
                    datos.getPrioridad(),
                    datos.getTipo(),
                    datos.getCitaOrigen(),
                    datos.getFechaCita(),
                    datos.getHoraInicio(),
                    datos.getHoraFin(),
                    datos.getConsultorio(),
                    datos.getEstablecimiento(),
                    datos.getIndicaciones());
            case VIRTUAL -> new CitaVirtual(
                    datos.getId(),
                    datos.getAgendaMedica(),
                    datos.getExpediente(),
                    datos.getOrden(),
                    datos.getEstadoCita(),
                    datos.getPrioridad(),
                    datos.getTipo(),
                    datos.getCitaOrigen(),
                    datos.getFechaCita(),
                    datos.getHoraInicio(),
                    datos.getHoraFin(),
                    datos.getUrl(),
                    datos.getPlataforma());
            case DOMICILIARIA -> new CitaDomiciliaria(
                    datos.getId(),
                    datos.getAgendaMedica(),
                    datos.getExpediente(),
                    datos.getOrden(),
                    datos.getEstadoCita(),
                    datos.getPrioridad(),
                    datos.getTipo(),
                    datos.getCitaOrigen(),
                    datos.getFechaCita(),
                    datos.getHoraInicio(),
                    datos.getHoraFin(),
                    datos.getDireccionVisita(),
                    datos.getReferenciaUbicacion());
        };
    }
}
