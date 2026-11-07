package sv.gob.salud.CitasService.registro;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

/**
 * Corazón del Proxy dinámico: se invoca en cada llamada al proxy, mide el tiempo, delega en el
 * objeto real y registra una {@link RegistroOperacion}. El negocio no se entera de que lo miden.
 *
 * <p>Es exactamente el mecanismo que Spring AOP usa por debajo: un {@code InvocationHandler} que
 * ejecuta el "aspecto" (aquí, el registro) alrededor de la llamada real.
 */
public class ManejadorRegistro implements InvocationHandler {

    private final Object real;
    private final Consumer<RegistroOperacion> bitacora;

    public ManejadorRegistro(Object real, Consumer<RegistroOperacion> bitacora) {
        this.real = real;
        this.bitacora = bitacora;
    }

    @Override
    public Object invoke(Object proxy, Method metodo, Object[] args) throws Throwable {
        // Los métodos de Object (toString, hashCode, equals) no son operaciones de negocio:
        // se delegan sin medir ni registrar. Si no, cualquier inspección del proxy (el panel de
        // variables del IDE, un logger, el depurador) llamaría a toString() y ensuciaría la
        // bitácora, volviendo el registro no determinista. Spring AOP hace esta misma exclusión.
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