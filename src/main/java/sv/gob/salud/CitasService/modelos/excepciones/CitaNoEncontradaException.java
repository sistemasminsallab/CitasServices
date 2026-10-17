package sv.gob.salud.CitasService.modelos.excepciones;

public class CitaNoEncontradaException extends RuntimeException {
    public CitaNoEncontradaException(String message) {
        super(message);
    }
}
