package sv.gob.salud.CitasService.registro;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Segundo aspecto sobre {@link sv.gob.salud.CitasService.servicios.OperacionesCita}: modo "solo
 * lectura" (mantenimiento). Deja pasar las consultas y bloquea cualquier operación que modifique
 * citas, sin tocar la lógica del servicio real.
 */
public class ManejadorSoloLectura implements InvocationHandler {

    private final Object real;

    public ManejadorSoloLectura(Object real) {
        this.real = real;
    }

    @Override
    public Object invoke(Object proxy, Method metodo, Object[] args) throws Throwable {
        // 1) Los métodos de Object (toString, hashCode, equals) no son del negocio: se delegan.
        if (metodo.getDeclaringClass() == Object.class) {
            return metodo.invoke(real, args);
        }

        // 2) ¿Es una operación de lectura? (nombre empieza con listar u obtener)
        String nombre = metodo.getName();
        boolean esLectura = nombre.startsWith("listar") || nombre.startsWith("obtener");

        // 3) Escritura: se bloquea; nunca llega al servicio real.
        if (!esLectura) {
            throw new IllegalStateException(
                "Sistema en modo solo lectura: no se permite '" + nombre + "'.");
        }

        // 4) Lectura: se delega en el servicio real y se devuelve su resultado.
        try {
            return metodo.invoke(real, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}