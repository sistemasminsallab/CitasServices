package sv.gob.salud.CitasService.servicios;

import org.springframework.stereotype.Service;

import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.modelos.fabricas.CitaFactory;
import sv.gob.salud.CitasService.modelos.fabricas.DatosCita;

@Service // scope singleton por defecto: una sola instancia en toda la app, sin getInstance()
public class CitaService {

    public Cita agendar(Modalidad modalidad, DatosCita datos) {
        return CitaFactory.crear(modalidad, datos);
    }
}
