package sv.gob.salud.CitasService.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sv.gob.salud.CitasService.modelos.excepciones.CitaNoDisponibleException;
import sv.gob.salud.CitasService.modelos.excepciones.CitaNoEncontradaException;

import java.util.Map;

/** Traduce las excepciones del dominio a respuestas HTTP en un solo lugar. */
@RestControllerAdvice
public class ManejadorErrores {

    @ExceptionHandler(CitaNoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> noEncontrada(CitaNoEncontradaException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(CitaNoDisponibleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> citaNoDisponible(CitaNoDisponibleException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> datosInvalidos(IllegalArgumentException e) {
        return Map.of("error", e.getMessage());
    }
}