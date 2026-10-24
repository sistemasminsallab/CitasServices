package sv.gob.salud.CitasService.modelos.fabricas;

import sv.gob.salud.CitasService.modelos.entidades.*;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;

public final class CitaFactory {

    private CitaFactory() { } // utilidad: no se instancia

    public static Cita crear(Modalidad modalidad, DatosCita d) {
        return switch (modalidad) {                 // ÚNICO switch de creación
            case PRESENCIAL -> new CitaPresencial.Builder()
                    .id(d.id()).agendaMedica(d.agendaMedica()).expediente(d.expediente())
                    .orden(d.orden()).estadoCita(d.estadoCita()).prioridad(d.prioridad())
                    .tipo(d.tipo()).citaOrigen(d.citaOrigen()).fechaCita(d.fechaCita())
                    .horaInicio(d.horaInicio()).horaFin(d.horaFin())
                    .consultorio(d.consultorio()).establecimiento(d.establecimiento())
                    .indicaciones(d.indicaciones())
                    .build();
            case DOMICILIARIA -> new CitaDomiciliaria.Builder()
                    .id(d.id()).agendaMedica(d.agendaMedica()).expediente(d.expediente())
                    .orden(d.orden()).estadoCita(d.estadoCita()).prioridad(d.prioridad())
                    .tipo(d.tipo()).citaOrigen(d.citaOrigen()).fechaCita(d.fechaCita())
                    .horaInicio(d.horaInicio()).horaFin(d.horaFin())
                    .direccionVisita(d.direccionVisita()).referenciaUbicacion(d.referenciaUbicacion())
                    .build();
            case VIRTUAL -> new CitaVirtual.Builder()
                    .id(d.id()).agendaMedica(d.agendaMedica()).expediente(d.expediente())
                    .orden(d.orden()).estadoCita(d.estadoCita()).prioridad(d.prioridad())
                    .tipo(d.tipo()).citaOrigen(d.citaOrigen()).fechaCita(d.fechaCita())
                    .horaInicio(d.horaInicio()).horaFin(d.horaFin())
                    .url(d.url()).plataforma(d.plataforma())
                    .build();
        };
    }
}
