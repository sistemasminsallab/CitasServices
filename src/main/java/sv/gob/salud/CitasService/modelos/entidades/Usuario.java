package sv.gob.salud.CitasService.modelos.entidades;

import sv.gob.salud.CitasService.modelos.enums.Roles;

public class Usuario {
    private String nombreUsuario;
    private Roles rolSistema;
    private boolean activo = true;
    // private String contrasena;

    // * Constructores
    public Usuario(String nombreUsuario, Roles rolSistema) {
        cambiarNombreUsuario(nombreUsuario);
        cambiarRol(rolSistema);
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

    // * Validar si el usuario tiene un rol específico
    public boolean tieneRol(Roles rolRequerido) {
        if (this.rolSistema == null || rolRequerido == null) {
            return false;
        }
        return this.rolSistema.equals(rolRequerido);
    }

    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }

    @Override
    public String toString() {
        return"Usuario{" +
        "nombreUsuario='" + nombreUsuario + '\'' +
        ", roles=" + rolSistema +
        ", activo=" + activo +
        '}';
    }
}
