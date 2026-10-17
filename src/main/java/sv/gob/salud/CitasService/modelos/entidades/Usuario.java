package sv.gob.salud.CitasService.modelos.entidades;

import java.util.Objects;

import sv.gob.salud.CitasService.modelos.enums.Roles;

public class Usuario {
    private String nombreUsuario;
    private Roles rolSistema;
    private boolean activo;
    private String contrasena;

    // * Constructores
    public Usuario() {}

    public Usuario(String nombreUsuario, Roles rol, boolean activo) {
        this.nombreUsuario = Objects.requireNonNull(nombreUsuario, "El nombre de usuario no puede ser nulo");
        this.rolSistema = Objects.requireNonNull(rol, "El rol no puede ser nulo");
        this.activo = Objects.requireNonNull(activo, "El estado del usuario no puede ser nulo");
    }

    // * Setters
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setRolSistema(Roles rolSistema) {
        this.rolSistema = Objects.requireNonNull(rolSistema, "El rol no puede ser nulo");
    }
    
    public void setActivo(boolean activo) {
        this.activo = Objects.requireNonNull(activo, "El estado del usuario no puede ser nulo");
    }

    // * Getters
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public Roles getRolSistema() {
        return rolSistema;
    }
    public boolean isActivo() {
        return activo;
    }

    @Override
    public String toString() {
        return"Usuario{" +
        "nombreUsuario='" + nombreUsuario + '\'' +
        ", roles=" + rolSistema +
        ", activo=" + activo +
        '}';
    }

    // * Validar si el usuario tiene un rol específico
    public boolean tieneRol(Roles rolRequerido) {
        if (this.rolSistema == null || rolRequerido == null) {
            return false;
        }
        return this.rolSistema.equals(rolRequerido);
    }

    public Usuario(String nombreUsuario, Roles rol) {
        cambiarNombreUsuario(nombreUsuario);
        cambiarRol(rol);
        this.activo = true;
    }

    public void cambiarNombreUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        this.nombreUsuario = nombreUsuario.trim();
    }

    public void cambiarRol(Roles rol) {
        if (rol == null) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }
        this.rolSistema = rol;
    }

    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }
}
