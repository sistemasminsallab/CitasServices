package sv.gob.salud.CitasService.modelos.enums;

public enum TipoEstablecimiento {
    HOSPITAL("Hospital"),
    CLINICA("Clínica"),
    CENTRO_SALUD("Centro de Salud"),
    UNIDAD_MEDICA("Unidad Médica"),
    CONSULTORIO("Consultorio Privado"),
    LABORATORIO("Laboratorio Clínico");
 
    private final String descripcion;
 
    TipoEstablecimiento(String descripcion) {
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
