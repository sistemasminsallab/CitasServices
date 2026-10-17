
package sv.gob.salud.CitasService.modelos.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import sv.gob.salud.CitasService.modelos.enums.EstadoCita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.enums.Prioridad;
import sv.gob.salud.CitasService.modelos.enums.TipoCita;
import sv.gob.salud.CitasService.modelos.validacion.*;


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

        /* validarDireccion(direccionVisita);

        if (direccionVisita == null || direccionVisita.isBlank()) {
            throw new IllegalArgumentException(
                "La dirección de visita es obligatoria"
            );
        }

        this.direccionVisita = direccionVisita.trim(); 
        this.referenciaUbicacion = normalizarReferencia( referenciaUbicacion); */
    }

    // Validar dirección
    private static String validarDireccion(String direccion) { 
        /* if (direccion == null || direccion.isBlank()) { 
            throw new IllegalArgumentException( "La dirección de visita es obligatoria");
        } */

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
        /*if (direccionVisita == null || direccionVisita.isBlank()) {
            throw new IllegalArgumentException(
                "La dirección de visita es obligatoria"
            );
        }*/

        this.direccionVisita = validarDireccion(direccionVisita);
        this.referenciaUbicacion = Validaciones.textoOpcional(referenciaUbicacion);
    }

    public String obtenerUbicacionCompleta() {
        // if (referenciaUbicacion == null || referenciaUbicacion.isBlank()) {
        // return direccionVisita;
        // }

        if (!tieneReferenciaUbicacion()) {
            return direccionVisita;
        }
        
        return direccionVisita + " - Referencia: " + referenciaUbicacion;
    }

    // Verificar si existe referencia 
    public boolean tieneReferenciaUbicacion() {
        return referenciaUbicacion != null;
    }

    // Mostrar datos domiciliarios    
    public String mostrarDatosDomiciliarios() {
            return "Dirección: " + direccionVisita
            + "\nReferencia: "
            + (tieneReferenciaUbicacion() ? referenciaUbicacion : "No especificada");
    }

    @Override
    public Modalidad modalidad() {
        return Modalidad.DOMICILIARIA;
    }

    @Override
    public String detalleLugar() {
        return mostrarDatosDomiciliarios();
    }
}