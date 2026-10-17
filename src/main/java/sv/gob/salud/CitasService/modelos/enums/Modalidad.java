package sv.gob.salud.CitasService.modelos.enums;

public enum Modalidad {
    PRESENCIAL("Presencial"),
    VIRTUAL("Virtual"),
    DOMICILIARIA("Domiciliaria");

    private final String descripcion;

    Modalidad(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
