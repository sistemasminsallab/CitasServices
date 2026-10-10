package sv.gob.salud.CitasService.modelos.entidades;

import sv.gob.salud.CitasService.modelos.enums.Roles;

public class Usuario extends Persona {
    private String nombreUsuario;
    private Roles roleSistema;

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public Roles getRoleSistema() {
        return roleSistema;
    }

    

}
