package sv.gob.salud.CitasService.registro;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/** Cuenta las invocaciones por nombre de método sin modificar la lógica del servicio. */
public class ManejadorContador implements InvocationHandler {

    private final Object real;
    private final Map<String, Integer> conteo;

    public ManejadorContador(Object real, Map<String, Integer> conteo) {
        this.real = real;
        this.conteo = conteo;
    }

    @Override
    public Object invoke(Object proxy, Method metodo, Object[] args) throws Throwable {
        // Inspeccionar el proxy no debe aumentar el contador de operaciones de negocio.
        if (metodo.getDeclaringClass() == Object.class) {
            return metodo.invoke(real, args);
        }

        // La primera llamada crea la entrada en 1; las siguientes suman 1.
        // Se cuenta antes de delegar para incluir los intentos que terminan con una excepción.
        conteo.merge(metodo.getName(), 1, Integer::sum);
        try {
            return metodo.invoke(real, args);
        } catch (InvocationTargetException e) {
            // Conserva el tipo de excepción de negocio que recibe el cliente.
            throw e.getCause();
        }
    }
}
