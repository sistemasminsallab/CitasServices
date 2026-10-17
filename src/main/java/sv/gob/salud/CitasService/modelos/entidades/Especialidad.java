package sv.gob.salud.CitasService.modelos.entidades;

public class Especialidad {
    private Long id;
    private String nombre;

    public Especialidad(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}