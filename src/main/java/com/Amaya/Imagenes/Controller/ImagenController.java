package com.Amaya.Imagenes.Controller;

import com.Amaya.Imagenes.Modelo.Datos.DatosRegistrarImagen;
import com.Amaya.Imagenes.Modelo.Datos.DatosRespuestaImagen;
import com.Amaya.Imagenes.Modelo.Imagen;
import com.Amaya.Imagenes.Repository.ImagenRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

@RestController
@RequestMapping("/imagen")
public class ImagenController {

    @Autowired
    private ImagenRepository imagenRepository;


    @PostMapping
    @Transactional
    public ResponseEntity registrarImagen(@RequestParam("file") MultipartFile imagen,
                                          UriComponentsBuilder uriComponentsBuilder) throws IOException {

        Imagen imagen1 = imagenRepository.save(new Imagen(imagen));

        DatosRespuestaImagen datosRespuestaImagen = new DatosRespuestaImagen(imagen.getBytes());

        URI uri = uriComponentsBuilder.path("/imagen/{id}").buildAndExpand(imagen1.getId()).toUri();

        return ResponseEntity.created(uri).body(datosRespuestaImagen);
    }

}
