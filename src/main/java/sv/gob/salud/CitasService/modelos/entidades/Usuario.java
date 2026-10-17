package sv.gob.salud.CitasService.modelos.entidades;

import sv.gob.salud.CitasService.modelos.enums.Roles;

/**
 * Usuario del sistema. No es una Persona: se relaciona con una, no hereda de
 * ella.
 */
public class Usuario {
    private String nombreUsuario;
    private Roles rolSistema;
    private boolean activo = true;

    public Usuario(String nombreUsuario, Roles rolSistema) {
        cambiarNombreUsuario(nombreUsuario);
        cambiarRol(rolSistema);
    }

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

    public boolean tieneRol(Roles rolRequerido) {
        return rolSistema == rolRequerido;
    }

    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }
}