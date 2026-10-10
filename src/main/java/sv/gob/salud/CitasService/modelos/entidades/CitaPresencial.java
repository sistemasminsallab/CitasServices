package sv.gob.salud.CitasService.modelos.entidades;

public class CitaPresencial extends Cita {
    private Long id;
    private String consultorio;
    private Establecimiento establecimiento;
    private String indicaciones;
    
    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public boolean tieneConsultorioAsignado() {
        if (consultorio == null || consultorio.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean tieneEstablecimientoAsignado() {
        if (establecimiento == null) {
            return false;
        } else {
            return true;
        }
    }

    public String obtenerInformacionDeCitaPresencial() {
        if (tieneConsultorioAsignado() && tieneEstablecimientoAsignado()) {
            return "Establecimiento: " + establecimiento.getNombre() + ", Consultorio: " + consultorio;
        } else {
            return "No se ha asignado un establecimiento o consultorio para la cita presencial";
        }
    }


}



