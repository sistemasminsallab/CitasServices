package sv.gob.salud.CitasService.modelos.enums;

import java.util.Set;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Estados por los que pasa una cita y las transiciones legales entre ellos.
 *
 * <p>Experto en información (GRASP): el estado es quien sabe a qué estados puede pasar, así que
 * la regla de la máquina de estados vive aquí y no repartida en servicios o controladores.</p>
 */
public enum EstadoCita {
    PROGRAMADA,
    CONFIRMADA,
    ATENDIDA,
    CANCELADA,
    NO_ASISTIO,
    REPROGRAMADA;

    /** Estados a los que se puede pasar desde este. Se asigna en el bloque estático de abajo. */
    private Set<EstadoCita> siguientes;

    static {
        PROGRAMADA.siguientes = Set.of(CONFIRMADA, REPROGRAMADA, CANCELADA);
        CONFIRMADA.siguientes = Set.of(ATENDIDA, NO_ASISTIO, REPROGRAMADA, CANCELADA);
        REPROGRAMADA.siguientes = Set.of(CONFIRMADA, REPROGRAMADA, CANCELADA);
        // Estados finales: una vez aquí, la cita no cambia más.
        ATENDIDA.siguientes = Set.of();
        CANCELADA.siguientes = Set.of();
        NO_ASISTIO.siguientes = Set.of();
    }

    /** ¿Es legal pasar de este estado al siguiente? */
    public boolean puedePasarA(EstadoCita siguiente) {
        return siguiente != null && siguientes.contains(siguiente);
    }

    /** Estado final: ya no admite más transiciones (ATENDIDA, CANCELADA, NO_ASISTIO). */
    public boolean esFinal() {
        return siguientes.isEmpty();
    }
}