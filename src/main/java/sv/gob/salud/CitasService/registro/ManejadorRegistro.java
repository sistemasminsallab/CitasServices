package sv.gob.salud.CitasService.registro;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

public class ManejadorRegistro implements InvocationHandler {

    private final Object real;
    private final Consumer<RegistroOperacion> bitacora;

    public ManejadorRegistro(Object real, Consumer<RegistroOperacion> bitacora) {
        this.real = real;
        this.bitacora = bitacora;
    }

    @Override
    public Object invoke(Object proxy, Method metodo, Object[] args) throws Throwable {
       
        if (metodo.getDeclaringClass() == Object.class) {
            return metodo.invoke(real, args);
        }
        long inicio = System.nanoTime();
        try {
            return metodo.invoke(real, args); // delega en el servicio real
        } catch (InvocationTargetException e) {
            // Propaga la excepción de negocio tal cual la lanzó el servicio real.
            throw e.getCause();
        } finally {
            bitacora.accept(new RegistroOperacion(metodo.getName(), System.nanoTime() - inicio));
        }
    }
}