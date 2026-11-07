package sv.gob.salud.CitasService.registro;

/**
 * Una línea de bitácora producida por el proxy: qué operación del servicio se invocó y cuánto tardó.
 *
 * @param metodo        nombre del método interceptado (p. ej. {@code confirmarCita})
 * @param duracionNanos tiempo que tardó la llamada real, en nanosegundos
 */
public record RegistroOperacion(String metodo, long duracionNanos) {
}
