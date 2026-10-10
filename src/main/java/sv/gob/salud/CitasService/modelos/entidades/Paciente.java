package sv.gob.salud.CitasService.modelos.entidades;

public class Paciente extends Persona {
    private String telefono;
    private String correoElectronico;
    
    public String getTelefono() {
        return telefono;
    }
    public String getCorreoElectronico() {
        return correoElectronico;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    
    
}