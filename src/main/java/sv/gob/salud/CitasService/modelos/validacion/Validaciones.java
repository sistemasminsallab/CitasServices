package sv.gob.salud.CitasService.modelos.validacion;

public class Validaciones {
    private Validaciones(){}

    /** Devuelv eel texto sin espacios laterales, o lanza una excepción si es nulo o vacío. */ 
    public static String textoObligatorio(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

    /** Devuelve el texto sin espacio laterales, o null si es nulo o vacío. */
    public static String textoOpcional(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor.trim();
    }
}
