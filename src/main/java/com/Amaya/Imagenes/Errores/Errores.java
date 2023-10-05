package com.Amaya.Imagenes.Errores;

import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Errores {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tratarError404() {
        return "<h1>Error 404</h1>\n<h3>La pagina en la que intentaste acceder no extiste</h3>";
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity tratarErrorSizeLimitExceeded() {
        return ResponseEntity.badRequest().body("La imagen no debe superar los 70KB");
    }

}
