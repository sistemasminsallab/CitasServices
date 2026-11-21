package sv.gob.salud.CitasService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

import sv.gob.salud.CitasService.dto.AgendarCitaSolicitud;
import sv.gob.salud.CitasService.modelos.entidades.AgendaMedica;
import sv.gob.salud.CitasService.modelos.entidades.Cita;
import sv.gob.salud.CitasService.modelos.entidades.RangoHorario;
import sv.gob.salud.CitasService.modelos.enums.Modalidad;
import sv.gob.salud.CitasService.repositorios.RepositorioMemoria;
import sv.gob.salud.CitasService.servicios.CitaServicio;

public class ConcurrenciaAgendaTest {
    private static final int CUPO = 10;
    private static final int HILOS = 40;
    private static final int RONDAS = 30;

    @Test 
    void variosHilosNoPuedenSobrepasarElCupoNiRepetirElOrden() throws Exception {
        int rondasConProblemas = 0;
        for (int ronda = 0; ronda < RONDAS; ronda++) {
            RangoHorario jornada = new RangoHorario(1L, LocalTime.of(0, 0), LocalTime.of(23, 59), Modalidad.PRESENCIAL);
            AgendaMedica agenda = new AgendaMedica(1L, null, null, LocalDate.now().plusDays(1), jornada, CUPO, true);
            RepositorioMemoria repositorio = new RepositorioMemoria(List.of(agenda));
            CitaServicio servicio = new CitaServicio(repositorio, repositorio, List.of());

            agendarAlMismoTiempo(servicio);

            int agendadas = agenda.getCitas().size();
            long ordenesDistintos = agenda.getCitas().stream().map(Cita::getOrden).distinct().count();
            if (agendadas > CUPO || ordenesDistintos != agendadas) {
                rondasConProblemas++;
            }
        }
        assertEquals(0, rondasConProblemas, "rondas con sobrecupo u órdenes repetidos");
    }

     private void agendarAlMismoTiempo(CitaServicio servicio) throws Exception {
        ExecutorService hilos = Executors.newFixedThreadPool(HILOS);
        CountDownLatch salida = new CountDownLatch(1);
        List<Future<?>> tareas = new ArrayList<>();
        for (int i = 0; i < HILOS; i++) {
            LocalTime inicio = LocalTime.of(0, 0).plusMinutes(30L * i);
            tareas.add(hilos.submit(() -> {
                try {
                    salida.await();   // todos esperan la misma señal de salida
                    servicio.agendar(new AgendarCitaSolicitud(1L, Modalidad.PRESENCIAL, inicio, inicio.plusMinutes(25)));
                } catch (Exception esperado) {
                    // la agenda llena rechaza con CitaNoDisponibleException: es lo correcto
                }
            }));
        }
        salida.countDown();
        for (Future<?> tarea : tareas) {
            tarea.get();
        }
        hilos.shutdown();
    }


}
