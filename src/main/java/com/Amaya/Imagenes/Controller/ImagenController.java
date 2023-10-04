package com.Amaya.Imagenes.Controller;

import com.Amaya.Imagenes.Modelo.Datos.DatosRespuestaImagen;
import com.Amaya.Imagenes.Modelo.Imagen;
import com.Amaya.Imagenes.Repository.ImagenRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ImagenController {

    @Autowired
    private ImagenRepository imagenRepository;


    @PostMapping
    @Transactional
    public ResponseEntity registrarImagen(@RequestParam("file") MultipartFile multipartFile,
                                          UriComponentsBuilder uriComponentsBuilder) throws IOException {

        Imagen imagen = imagenRepository.save(new Imagen(multipartFile));

        DatosRespuestaImagen datosRespuestaImagen = new DatosRespuestaImagen(imagen.getId(), imagen.getImagen());

        URI uri = uriComponentsBuilder.path("/imagen/{id}").buildAndExpand(imagen.getId()).toUri();

        return ResponseEntity.created(uri).body(datosRespuestaImagen);
    }

    @GetMapping("/MostrarImagen/{id}")
    public void listarImagenPorId(@PathVariable long id, Model model,
                                    HttpServletResponse response) throws IOException {

        Imagen imagen = imagenRepository.getReferenceById(id);

        model.addAttribute("id", imagen.getId());

        response.setContentType("image/jpeg, image/jpg, image/png");

        var imagenSalida = response.getOutputStream();
        imagenSalida.write(imagen.getImagen());
        imagenSalida.flush();
        imagenSalida.close();

        model.addAttribute("imagen", imagenSalida);

    }

    @GetMapping("/")
    public String listarImagenes(Model model){

        List<byte[]> imagen = imagenRepository.getAllImagen();
        model.addAttribute("images", imagen);

        return "imagenes";

    }
}
