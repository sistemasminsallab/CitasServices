package sv.gob.salud.CitasService.modelos.enums;

public enum Modalidad {
    PRESENCIAL("Presencial"),
    VIRTUAL("Virtual"),
    TELEFONICA("Domiciliaria");

    private final String descripcion;

    Modalidad(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
