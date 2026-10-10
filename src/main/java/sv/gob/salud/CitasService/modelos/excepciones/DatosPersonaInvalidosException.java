package sv.gob.salud.CitasService.modelos.excepciones;

public class DatosPersonaInvalidosException extends IllegalArgumentException {
    public DatosPersonaInvalidosException(String mensaje) {
        super(mensaje);
    }
}