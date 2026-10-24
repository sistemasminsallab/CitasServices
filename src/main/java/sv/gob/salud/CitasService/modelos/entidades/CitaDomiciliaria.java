
package sv.gob.salud.CitasService.modelos.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.modelos.validacion.Validaciones;

public class CitaDomiciliaria extends Cita {

    private String direccionVisita;
    private String referenciaUbicacion;

    public CitaDomiciliaria(
            Long id,
            AgendaMedica agendaMedica,
            Expediente expediente,
            int orden,
            EstadoCita estadoCita,
            Prioridad prioridad,
            TipoCita tipo,
            Cita citaOrigen,
            LocalDate fechaCita,
            LocalTime horaInicio,
            LocalTime horaFin,
            String direccionVisita,
            String referenciaUbicacion) {

        super(
            id,
            agendaMedica,
            expediente,
            orden,
            estadoCita,
            prioridad,
            tipo,
            citaOrigen,
            fechaCita,
            horaInicio,
            horaFin
        );

        this.direccionVisita = validarDireccion(direccionVisita);
        this.referenciaUbicacion = Validaciones.textoOpcional(referenciaUbicacion);
    }

    private static String validarDireccion(String direccion) {
        return Validaciones.textoObligatorio(direccion, "La dirección de visita es obligatoria");
    }

    // Getters
    public String getDireccionVisita() {
        return direccionVisita;
    }

    public String getReferenciaUbicacion() {
        return referenciaUbicacion;
    }

    public void actualizarDireccion(String direccionVisita, String referenciaUbicacion) {
        this.direccionVisita = validarDireccion(direccionVisita);
        this.referenciaUbicacion = Validaciones.textoOpcional(referenciaUbicacion);
    }

    public String obtenerUbicacionCompleta() {
        if (!tieneReferenciaUbicacion()) {
            return direccionVisita;
        }
        return direccionVisita + " - Referencia: " + referenciaUbicacion;
    }

    public boolean tieneReferenciaUbicacion() {
        return referenciaUbicacion != null;
    }

    public String mostrarDatosDomiciliarios() {
        return "Dirección: " + direccionVisita
                + "\nReferencia: " + (tieneReferenciaUbicacion() ? referenciaUbicacion : "No especificada");
    }

    @Override
    public Modalidad modalidad() {
        return Modalidad.DOMICILIARIA;
    }

    @Override
    public String detalleLugar() {
        return obtenerUbicacionCompleta();
    }
}
