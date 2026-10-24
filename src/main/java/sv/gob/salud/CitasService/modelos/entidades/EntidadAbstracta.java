package sv.gob.salud.CitasService.modelos.entidades;


public abstract class EntidadAbstracta {
    
    private String uuid;
    private Long id;

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    } 

    
}
