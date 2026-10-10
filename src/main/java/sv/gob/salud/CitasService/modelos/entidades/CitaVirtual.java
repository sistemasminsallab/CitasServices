package sv.gob.salud.CitasService.modelos.entidades;

public class CitaVirtual {
    private Long id;
    private String url;
    private String plataforma;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public boolean tieneUrlAsignada() {
        if (url == null || url.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean tienePlataformaAsignada() {
        if (plataforma == null || plataforma.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public String enlaceDeCitaVirtual() {
        if (tieneUrlAsignada() && tienePlataformaAsignada()) {
            return plataforma + ": " + url;
        } else {
            return "No se ha asignado un enlace de cita virtual";
        }
    }
}
