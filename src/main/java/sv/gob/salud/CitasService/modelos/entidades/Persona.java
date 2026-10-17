package sv.gob.salud.CitasService.modelos.entidades;
import sv.gob.salud.CitasService.modelos.enums.Genero;
import sv.gob.salud.CitasService.modelos.excepciones.DatosPersonaInvalidosException;
import sv.gob.salud.CitasService.modelos.validacion.Validaciones;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Persona {
    //private Integer id;
    private String documentoidentidad;
    private String nombre;
    private String apellido;
    private Genero genero;
    private LocalDate fechaNacimiento;
    private String numeroTelefono;
    private String correoElectronico;

    //Constructores -----------------------------------
    public Persona(String documentoidentidad, String nombre, String apellido, Genero genero, LocalDate fechaNacimiento, String numeroTelefono, String correoElectronico) {
        setDocumentoIdentidad(documentoidentidad);
        setNombre(nombre);
        setApellido(apellido);
        setGenero(genero);
        setFechaNacimiento(fechaNacimiento);
        setTelefono(numeroTelefono);
        setCorreoElectronico(correoElectronico);
    }

    //Getters y Setters -----------------------------------

    public String getDocumentoIdentidad() {
        return documentoidentidad;
    }

    public void setDocumentoIdentidad(String documentoidentidad){
       if(Dui.esValido(documentoidentidad)){
           this.documentoidentidad = documentoidentidad;
       } else {
           throw new DatosPersonaInvalidosException("El DUI ingresado no es válido: " + documentoidentidad);    
       }
    }

     public String getNombres() {
        return nombre;
    }

    public void setNombre(String nombres) {
        this.nombre = Validaciones.textoObligatorio(nombres, "Nombre no puede ser nulo o vacío");
    }

    public String getApellidos() {
        return apellido;
    }

    public void setApellido(String apellidos) {
        this.apellido = Validaciones.textoObligatorio(apellidos, "Apellido no puede ser nulo o vacío");
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        if (Objects.isNull(genero)) {
            throw new DatosPersonaInvalidosException("Género no puede ser nulo");
        }
        this.genero = genero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (Objects.isNull(fechaNacimiento)) {
            throw new DatosPersonaInvalidosException("Fecha de nacimiento no puede ser nula");
        }
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new DatosPersonaInvalidosException("Fecha de nacimiento no puede ser futura");
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        // El correo electrónico puede ser opcional, pero si se ingresa debe ser válido
        if (correoElectronico != null && !correoElectronico.trim().isEmpty()) {
            if (!correoElectronico.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new IllegalArgumentException("Formato de correo electrónico inválido");
            }
        }
        this.correoElectronico = correoElectronico != null ? correoElectronico.trim() : null;
    }

    public String getTelefono() {
        return numeroTelefono;
    }

    public void setTelefono(String numeroTelefono) {
        // El teléfono puede ser opcional, pero si se ingresa debe ser válido
        if (numeroTelefono != null && !numeroTelefono.trim().isEmpty()) {
            if (!numeroTelefono.matches("\\+?[0-9\\s\\-()]{7,20}")) {
                throw new IllegalArgumentException("Formato de teléfono inválido");
            }
        }
        this.numeroTelefono = numeroTelefono != null ? numeroTelefono.trim() : null;
    }

    public String getNombreCompleto() {
        return (Objects.isNull(nombre) ? "" : nombre) + " " + (Objects.isNull(apellido) ? "" : apellido);
    }

    public int edad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public boolean tieneContactoRegistrado() { 
        return (correoElectronico != null && !correoElectronico.isBlank()) || (numeroTelefono != null && !numeroTelefono.isBlank()); 
    }


}