package sv.gob.salud.CitasService.modelos.excepciones;

public class CitaExcepcion {
    private String mensaje;

    public CitaExcepcion(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
