package sv.gob.salud.CitasService.modelos.entidades;

public class Expediente {
    private Long id;
    private String numero;
    private Paciente paciente;

    public Expediente(Long id, String numero, Paciente paciente) {
        this.id = id;
        this.numero = numero;
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public Paciente getPaciente() {
        return paciente;
    }

}
