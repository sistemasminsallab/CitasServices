package sv.gob.salud.CitasService.registro;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tercer aspecto sobre {@link sv.gob.salud.CitasService.servicios.OperacionesCita}: cuenta cuántas
 * veces se invoca cada operación del servicio real.
 *
 * <p>Se cuenta cada llamada (incluidas las que terminan en excepción) antes de delegar, sin tocar
 * la lógica de negocio. El cliente usa la misma interfaz y no nota que hay un proxy en medio.
 */
public class ManejadorContador implements InvocationHandler {

    private final Object real;
    private final Map<String, Integer> conteo;

    public ManejadorContador(Object real) {
        this(real, new ConcurrentHashMap<>());
    }

    public ManejadorContador(Object real, Map<String, Integer> conteo) {
        this.real = real;
        this.conteo = conteo;
    }

    @Override
    public Object invoke(Object proxy, Method metodo, Object[] args) throws Throwable {
        // Los métodos de Object (toString, hashCode, equals) no son operaciones de negocio.
        if (metodo.getDeclaringClass() == Object.class) {
            return metodo.invoke(real, args);
        }
        conteo.merge(metodo.getName(), 1, Integer::sum);
        try {
            return metodo.invoke(real, args); // delega en el servicio real
        } catch (InvocationTargetException e) {
            // Propaga la excepción de negocio tal cual la lanzó el servicio real.
            throw e.getCause();
        }
    }

    /** Cuántas veces se invocó cada operación, como vista inmutable. */
    public Map<String, Integer> conteos() {
        return Map.copyOf(conteo);
    }

    /** Cuántas veces se invocó una operación concreta (0 si nunca se llamó). */
    public int veces(String metodo) {
        return conteo.getOrDefault(metodo, 0);
    }

    /** Total de operaciones contadas. */
    public int total() {
        return conteo.values().stream().mapToInt(Integer::intValue).sum();
    }
}
