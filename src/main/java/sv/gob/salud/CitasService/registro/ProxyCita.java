package sv.gob.salud.CitasService.registro;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.function.Consumer;

import sv.gob.salud.CitasService.servicios.OperacionesCita;

/**
 * Fábrica del Proxy dinámico sobre {@link OperacionesCita}.
 *
 * <p>Devuelve un objeto que tiene la <b>misma interfaz</b> que el servicio real; el cliente lo usa
 * igual y no nota que hay un proxy en medio. Cada llamada pasa por {@link ManejadorRegistro}.
 */
public final class ProxyCita {

    private ProxyCita() {
    }

    /** Envuelve el servicio real con registro de operaciones (método + duración). */
    public static OperacionesCita conRegistro(OperacionesCita real, Consumer<RegistroOperacion> bitacora) {
        return (OperacionesCita) Proxy.newProxyInstance(
                real.getClass().getClassLoader(),
                new Class<?>[] { OperacionesCita.class },
                new ManejadorRegistro(real, bitacora));
    }

    /** Envuelve el servicio en modo solo lectura: bloquea las operaciones que modifican. */
    public static OperacionesCita soloLectura(OperacionesCita real) {
        return (OperacionesCita) Proxy.newProxyInstance(
                real.getClass().getClassLoader(),
                new Class<?>[] { OperacionesCita.class },
                new ManejadorSoloLectura(real));
    }

    /** Envuelve el servicio contando cuántas veces se llama cada método. */
    public static OperacionesCita conContador(OperacionesCita real, Map<String, Integer> conteo) {
        return (OperacionesCita) Proxy.newProxyInstance(
                real.getClass().getClassLoader(),
                new Class<?>[] { OperacionesCita.class },
                new ManejadorContador(real, conteo));
    }
}