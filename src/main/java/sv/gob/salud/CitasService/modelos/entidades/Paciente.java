package sv.gob.salud.CitasService.modelos.entidades;

import java.time.LocalDate;
import sv.gob.salud.CitasService.modelos.enums.Genero;

public class Paciente extends Persona {
    private String codigoPaciente;

    //Constructores -----------------------------------
    public Paciente(String documentoidentidad, String nombre, String apellido, Genero genero, LocalDate fechaNacimiento, String numeroTelefono, String correoElectronico, String codigoPaciente) {
        super(documentoidentidad, nombre, apellido, genero, fechaNacimiento, numeroTelefono, correoElectronico);
        setCodigoPaciente(codigoPaciente);
    }

    //Getters y Setters -----------------------------------
    public String getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(String codigoPaciente) {
        if (codigoPaciente == null || !codigoPaciente.trim().matches("^\\d{4}-\\d{2}$")) {
            throw new IllegalArgumentException(
                    "El código de paciente debe tener el formato ####-## (ej. 1234-24): " + codigoPaciente);
        }
        this.codigoPaciente = codigoPaciente.trim();
    }
}