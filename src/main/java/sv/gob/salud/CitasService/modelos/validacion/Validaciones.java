package sv.gob.salud.CitasService.modelos.validacion;

/** Reglas de validación compartidas: un solo lugar para cada regla repetida (DRY). */
public class Validaciones {
   private Validaciones(){}

   
    /** Devuelve el texto sin espacios laterales, o lanza si es nulo o vacío. */
   public static String textoObligatorio(String valor, String mensaje){
     if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
   }


   /** Devuelve el texto sin espacios laterales, o null si es nulo o vacío. */
    public static String textoOpcional(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }

}
