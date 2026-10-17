package sv.gob.salud.CitasService.modelos.entidades;

import java.time.LocalDateTime;
import java.util.List;

import sv.gob.salud.CitasService.modelos.enums.EstadoEstablecimiento;
import sv.gob.salud.CitasService.modelos.enums.TipoEstablecimiento;

public class Establecimiento {
    private Long id;
    private String codigo;
    private String nombre;
    private String direccion;
    private String correo;
    private String telefono;
    private TipoEstablecimiento tipo;
    private EstadoEstablecimiento estado;
    private List<Especialidad> especialidades;

    private LocalDateTime inicioAtencion;
    private LocalDateTime finAtencion;

    public Establecimiento(Long id, String codigo, String nombre, String direccion) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Long getId() {return id;}
    public String getCodigo() {return codigo;}
    public String getNombre() {return nombre;}
    public String getDireccion() {return direccion;}


    private int countEspecialidades () {
        return this.especialidades.size();
    }

    private String rangoAtencion() {
        return String.format("%s - %s", this.inicioAtencion, this.finAtencion);
    }

    private String fichaInformativaEstablecimiento() {
        return String.format(
            "codigo: %s /n establecimiento: %s /n dirección: %s /n correo: %s /n telefono: %s", 
            this.codigo, this.nombre, this.direccion, this.correo, this.telefono );
    }

}