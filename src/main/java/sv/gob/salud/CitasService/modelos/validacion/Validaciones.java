package sv.gob.salud.CitasService.modelos.validacion;

/** Reglas de validación compartidas: un solo lugar para cada regla repetida (DRY). */
public final class Validaciones {
    private Validaciones() { }

    public static String textoObligatorio(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

    public static String textoOpcional(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }
}
