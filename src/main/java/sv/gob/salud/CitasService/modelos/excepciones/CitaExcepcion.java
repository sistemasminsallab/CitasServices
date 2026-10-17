package sv.gob.salud.CitasService.modelos.excepciones;

public class CitaExcepcion extends RuntimeException {

    public CitaExcepcion(String mensaje) {
        super(mensaje);
    }
}