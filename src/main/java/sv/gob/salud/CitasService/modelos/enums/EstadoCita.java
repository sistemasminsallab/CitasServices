package sv.gob.salud.CitasService.modelos.enums;

import java.util.Set;

public enum EstadoCita {
    PROGRAMADA,
    CONFIRMADA,
    ATENDIDA,
    CANCELADA,
    NO_ASISTIO,
    REPROGRAMADA;

    private Set<EstadoCita> siguientes;

    static {
        PROGRAMADA.siguientes = Set.of(CONFIRMADA, REPROGRAMADA, CANCELADA);
        CONFIRMADA.siguientes = Set.of(ATENDIDA, NO_ASISTIO, REPROGRAMADA, CANCELADA);
        REPROGRAMADA.siguientes = Set.of(CONFIRMADA, REPROGRAMADA, CANCELADA);

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
 