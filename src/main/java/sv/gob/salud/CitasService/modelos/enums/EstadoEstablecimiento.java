package sv.gob.salud.CitasService.modelos.enums;

public enum EstadoEstablecimiento {
    ACTIVO("Activo"),
    INACTIVO("Inactivo"),
    EN_MANTENIMIENTO("En Mantenimiento");
 
    private final String descripcion;
 
    EstadoEstablecimiento(String descripcion) {
        this.descripcion = descripcion;
    }
 
    public String getDescripcion() {
        return descripcion;
    }
 
    @Override
    public String toString() {
        return descripcion;
    }
}