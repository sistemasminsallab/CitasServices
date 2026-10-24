package sv.gob.salud.CitasService.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sv.gob.salud.CitasService.servicios.CitaServicio;

/** Controlador: recibe, delega y responde. Cero reglas de negocio. */
@RestController
@RequestMapping("/api")
public class CitaControlador {
    private final CitaServicio servicio;

    public CitaControlador(CitaServicio servicio) {
        this.servicio = servicio;
    }

    // @GetMapping("/citas")
    // public List<CitaRespuesta> listarCitas() {
    //     return servicio.listarCitas().stream().map(CitaRespuesta::de).toList();
    // }

    // @GetMapping("/citas/{id}")
    // public CitaRespuesta obtenerCita(@PathVariable Long id) {
    //     return CitaRespuesta.de(servicio.obtenerCita(id));
    // }

    // @PostMapping("/citas")
    // @ResponseStatus(HttpStatus.CREATED)
    // public CitaRespuesta agendar(@RequestBody AgendarCitaSolicitud solicitud) {
    //     return CitaRespuesta.de(servicio.agendar(solicitud));
    // }

    // @GetMapping("/agendas")
    // public List<AgendaRespuesta> listarAgendas() {
    //     return servicio.listarAgendas().stream().map(AgendaRespuesta::de).toList();
    // }
}
