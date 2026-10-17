package sv.gob.salud.CitasService.modelos.entidades;

import sv.gob.salud.CitasService.modelos.enums.Genero;

import java.time.LocalDate;
import java.util.*;

public class Empleado extends Persona {
    private String jvpm;
    private Set<Especialidad> especialidades;
    private List<AgendaMedica> agendasMedicas;
    private String descripcion;


    public Empleado(String documentoidentidad,
                    String nombre,
                    String apellido,
                    Genero genero,
                    LocalDate fechaNacimiento,
                    String numeroTelefono,
                    String correoElectronico,
                    String jvpm
                    ) {
        super(documentoidentidad, nombre, apellido, genero, fechaNacimiento, numeroTelefono, correoElectronico);
        this.jvpm = Objects.requireNonNull(jvpm, "El jvpm no puede ser nulo");
        this.especialidades = new HashSet<>();
        this.agendasMedicas = new ArrayList<>();
    }

    public String getJvpm() {
        return jvpm;
    }

    public Set<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public List<AgendaMedica> getAgendasMedicas() {
        return agendasMedicas;
    }

    public void setJvpm(String jvpm) {
        this.jvpm = jvpm;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean tieneEspecialidadesAsignadas() {
        return especialidades != null && especialidades.isEmpty();
    }

    public boolean tieneAgendasMedicasAsignadas() {
        return agendasMedicas != null && agendasMedicas.isEmpty();

    }

    public int cantidadDeEspecialidadesAsignadas() {
        return especialidades.size();
    }
    
    
    

}
