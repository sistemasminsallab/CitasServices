package sv.gob.salud.CitasService.modelos.excepciones;

public class CitaExcepcion extends RuntimeException {
    // private String mensaje;

    public CitaExcepcion(String mensaje) {
        super(mensaje);
    }
}