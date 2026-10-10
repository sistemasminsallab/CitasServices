package sv.gob.salud.CitasService.modelos.excepciones;

public class CitaNoDisponibleException {
    private String mensaje;

    public CitaNoDisponibleException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}