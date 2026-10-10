package sv.gob.salud.CitasService.modelos.entidades;

public class Prioridad {
    private String nivel;

    public Prioridad(String nivel) {
        this.nivel = nivel;
    }

    // Getters and Setters
    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
