package sv.gob.salud.CitasService.modelos.excepciones;

public class DatosPersonaInvalidosException {
    private String mensaje;

    public DatosPersonaInvalidosException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
