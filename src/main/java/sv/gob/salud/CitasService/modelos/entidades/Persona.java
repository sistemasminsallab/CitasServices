package sv.gob.salud.CitasService.modelos.entidades;
import sv.gob.salud.CitasService.modelos.enums.Genero;
import sv.gob.salud.CitasService.modelos.excepciones.DatosPersonaInvalidosException;
import sv.gob.salud.CitasService.modelos.validacion.Validaciones;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.regex.Pattern;

public class Persona {
    //private Integer id;
    private String documentoidentidad;
    private String nombre;
    private String apellido;
    private Genero genero;
    private LocalDate fechaNacimiento;
    private String numeroTelefono;
    private String correoElectronico;

    private static final Pattern FORMATO_DUI = Pattern.compile("^\\d{8}-\\d$"); 
    private static final String DUI_CEROS = "00000000-0";

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
       if(esDuiValido(documentoidentidad)){
           this.documentoidentidad = documentoidentidad;
       } else {
           throw new DatosPersonaInvalidosException("El DUI ingresado no es válido: " + documentoidentidad);    
       }
    }

     public String getNombres() {
        return nombre;
    }

    public void setNombre(String nombres) {
        this.nombre = Validaciones.textoObligatorio(nombre, "Nombre no puede ser nulo o vacío");
    }

    public String getApellidos() {
        return apellido;
    }

    public void setApellido(String apellidos) {
        this.apellido = Validaciones.textoObligatorio(apellido, "apellido no puede ser nulo o vacío");
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
        this.correoElectronico = Validaciones.textoObligatorio(correoElectronico, "correoElectronico no puede ser nulo o vacío");
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

    //Metodos -----------------------------------
    public static boolean esDuiValido(String dui) { 
        if (dui == null || ! FORMATO_DUI.matcher(dui.trim()).matches() || DUI_CEROS.equals(dui.trim())) { 
            return false; 
        } 
        String[] partes = dui.trim().split("-"); String correlativo = partes[0]; 
        int digitoVerificadorEsperado = Integer.parseInt(partes[1]); 
        int suma = 0; 
        int ponderacion = 9; 
        for (int i = 0; i < correlativo.length(); i++) { 
            int digito = Character.getNumericValue(correlativo.charAt(i)); suma += digito * ponderacion; ponderacion--; 
        } 
        int mod = suma % 10; 
        int residuo = 10 - mod; if (residuo == 10) { 
            residuo = 0; 
        } 
        return residuo == digitoVerificadorEsperado; 
    }

    public String getNombreCompleto() {
        return (Objects.isNull(nombre) ? "" : nombre) + " " + (Objects.isNull(apellido) ? "" : apellido);
    }

    public int edad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public boolean tieneContactoRegistrado() {
        return correoElectronico != null || numeroTelefono != null; 
    }
}