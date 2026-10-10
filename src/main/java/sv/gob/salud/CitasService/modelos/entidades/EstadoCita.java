package sv.gob.salud.CitasService.modelos.entidades;

public class EstadoCita {
    private String estado;

    public EstadoCita(String estado) {
        this.estado = estado;
    }

    // Getters and Setters
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
