package sv.gob.salud.CitasService.registro;

import java.lang.reflect.Proxy;
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
}