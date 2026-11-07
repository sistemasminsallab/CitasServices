package sv.gob.salud.CitasService.registro;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ManejadorSoloLectura implements InvocationHandler {

    private final Object real;

    public ManejadorSoloLectura(Object real) {
        this.real = real;
    }

    @Override
    public Object invoke(Object proxy, Method metodo, Object[] args) throws Throwable {
        
        if (metodo.getDeclaringClass() == Object.class) {
            return metodo.invoke(real, args);
        }

        String nombre = metodo.getName();
        boolean esLectura = nombre.startsWith("listar") || nombre.startsWith("obtener");

        if (!esLectura) {
            throw new IllegalStateException("Sistema en modo solo lectura: no se permite " + nombre + ".");
        }
        
        try {
            return metodo.invoke(real, args);
        } catch (Exception e) {
            throw e.getCause();
        }
    }

}
