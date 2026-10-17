package sv.gob.salud.CitasService.modelos.entidades;

import java.time.LocalTime;
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
    private LocalTime inicioAtencion;
    private LocalTime finAtencion;

    public Establecimiento(Long id, String codigo, String nombre, String direccion, String correo, String telefono, TipoEstablecimiento tipo, EstadoEstablecimiento estado, List<Especialidad> especialidades, LocalTime inicioAtencion, LocalTime finAtencion) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
        this.tipo = tipo;
        this.estado = estado;
        this.especialidades = especialidades;
        this.inicioAtencion = inicioAtencion;
        this.finAtencion = finAtencion;
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public TipoEstablecimiento getTipo() { return tipo; }
    public EstadoEstablecimiento getEstado() { return estado; }
    public List<Especialidad> getEspecialidades() { return especialidades; }
    public LocalTime getInicioAtencion() { return inicioAtencion; }
    public LocalTime getFinAtencion() { return finAtencion; }
}