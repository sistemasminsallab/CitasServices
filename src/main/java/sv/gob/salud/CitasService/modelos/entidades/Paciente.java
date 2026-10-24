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

    public static class Builder {
        private Long id;
        private String documentoidentidad;
        private String nombre;
        private String apellido;
        private Genero genero;
        private LocalDate fechaNacimiento;
        private String numeroTelefono;
        private String correoElectronico;
        private String codigoPaciente;

        public Builder setDocumentoidentidad(String documentoidentidad) { this.documentoidentidad = documentoidentidad; return this; }
        public Builder setNombre(String nombre) { this.nombre = nombre; return this; }
        public Builder setApellido(String apellido) { this.apellido = apellido; return this; }
        public Builder setGenero(Genero genero) { this.genero = genero; return this; }
        public Builder setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; return this; }
        public Builder setNumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; return this; }
        public Builder setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; return this; }
        public Builder setCodigoPaciente(String codigoPaciente) { this.codigoPaciente = codigoPaciente; return this; }

        public Paciente build() {
            return new Paciente(documentoidentidad, nombre, apellido, genero, fechaNacimiento, numeroTelefono, correoElectronico, codigoPaciente);
        }
    }
}