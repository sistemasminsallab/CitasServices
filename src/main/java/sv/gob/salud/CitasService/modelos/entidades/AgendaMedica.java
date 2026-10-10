package sv.gob.salud.CitasService.modelos.entidades;


public class AgendaMedica {
    private String fecha;
    private String hora;
    private String medico;
    private String especialidad;

    public AgendaMedica(String fecha, String hora, String medico, String especialidad) {
        this.fecha = fecha;
        this.hora = hora;
        this.medico = medico;
        this.especialidad = especialidad;
    }

    // Getters and Setters
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
