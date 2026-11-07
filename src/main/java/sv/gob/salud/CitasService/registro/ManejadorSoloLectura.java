package sv.gob.salud.CitasService.registro;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Aspecto de mantenimiento: permite consultas y bloquea modificaciones antes de llegar al servicio.
 * La regla se aplica en el proxy, sin cambiar CitaServicio ni OperacionesCita.
 */
public class ManejadorSoloLectura implements InvocationHandler {

    private final Object real;

    public ManejadorSoloLectura(Object real) {
        this.real = real;
    }

    @Override
    public Object invoke(Object proxy, Method metodo, Object[] args) throws Throwable {
        // El IDE y los loggers usan métodos de Object; no son escrituras del negocio.
        if (metodo.getDeclaringClass() == Object.class) {
            return metodo.invoke(real, args);
        }

        // La guía clasifica como consultas los métodos que empiezan con listar u obtener.
        String nombre = metodo.getName();
        boolean esLectura = nombre.startsWith("listar") || nombre.startsWith("obtener");
        if (!esLectura) {
            // Se rechaza antes de delegar para evitar cualquier modificación en el servicio real.
            throw new IllegalStateException(
                    "Sistema en modo solo lectura: no se permite '" + nombre + "'.");
        }

        try {
            return metodo.invoke(real, args);
        } catch (InvocationTargetException e) {
            // La reflexión envuelve la excepción; el cliente debe recibir la causa original.
            throw e.getCause();
        }
    }
}
