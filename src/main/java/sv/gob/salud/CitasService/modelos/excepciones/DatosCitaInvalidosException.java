package sv.gob.salud.CitasService.modelos.excepciones;

public class DatosCitaInvalidosException {
    private String mensaje;

    public DatosCitaInvalidosException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
