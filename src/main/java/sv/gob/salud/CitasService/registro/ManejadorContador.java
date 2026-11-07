package sv.gob.salud.CitasService.registro;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Tercer aspecto: cuenta cuántas veces se invocó cada método del servicio, en un mapa
 * nombre-del-método -> número de llamadas. No toca la lógica del negocio.
 */
public class ManejadorContador implements InvocationHandler {

    private final Object real;
    private final Map<String, Integer> conteo;

    public ManejadorContador(Object real, Map<String, Integer> conteo) {
        this.real = real;
        this.conteo = conteo;
    }

    @Override
    public Object invoke(Object proxy, Method metodo, Object[] args) throws Throwable {
        if (metodo.getDeclaringClass() == Object.class) { // igual que siempre
            return metodo.invoke(real, args);
        }
        conteo.merge(metodo.getName(), 1, Integer::sum); // +1 a este método
        try {
            return metodo.invoke(real, args); // delega
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
