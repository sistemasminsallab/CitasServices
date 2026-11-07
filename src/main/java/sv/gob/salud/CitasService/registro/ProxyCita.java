package sv.gob.salud.CitasService.registro;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.function.Consumer;

import sv.gob.salud.CitasService.servicios.OperacionesCita;

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

    public static OperacionesCita soloLectura(OperacionesCita real){

        return (OperacionesCita) Proxy.newProxyInstance(
                real.getClass().getClassLoader(),
                new Class<?>[] { OperacionesCita.class },
                new ManejadorSoloLectura(real)
        );

    }

     public static OperacionesCita conContador(OperacionesCita real, Map<String, Integer> conteo) {
        return (OperacionesCita) Proxy.newProxyInstance(
                real.getClass().getClassLoader(),
                new Class<?>[] { OperacionesCita.class },
                new ManejadorContador(real, conteo));
    }
}