package com.Amaya.Imagenes.Controller;

import com.Amaya.Imagenes.Modelo.Datos.DatosRespuestaImagen;
import com.Amaya.Imagenes.Modelo.Imagen;
import com.Amaya.Imagenes.Repository.ImagenRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
@RequestMapping("/")
@Tag(name = "Imagen")
public class ImagenController {

    @Autowired
    private ImagenRepository imagenRepository;


    @PostMapping("/registrar")
    @Transactional
    @Operation(summary = "Registra una imagen")
    public ResponseEntity registrarImagen(@RequestParam("file") MultipartFile multipartFile,
                                          UriComponentsBuilder uriComponentsBuilder) throws IOException {

        Imagen imagen = imagenRepository.save(new Imagen(multipartFile));

        DatosRespuestaImagen datosRespuestaImagen = new DatosRespuestaImagen(imagen.getId(), imagen.getImagen());

        URI uri = uriComponentsBuilder.path("/imagen/{id}").buildAndExpand(imagen.getId()).toUri();

        return ResponseEntity.created(uri).body(datosRespuestaImagen);
    }

    @PutMapping("/modificar/{id}")
    @Transactional
    @Operation(summary = "Modifica una imagen")
    public ResponseEntity ActualizarTopico(@PathVariable long id, @RequestParam("file") MultipartFile multipartFile)
            throws IOException {

        if (multipartFile.isEmpty()) {

            return ResponseEntity.badRequest().body("No puede estar vacio");

        } else {

            Imagen imagen = imagenRepository.getReferenceById(id);

            imagen.actualizarImagen(multipartFile);

            DatosRespuestaImagen datosRespuestaImagen = new DatosRespuestaImagen(imagen.getId(), imagen.getImagen());

            return ResponseEntity.ok(datosRespuestaImagen);

        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista imagen por id")
    public String listarImagenPorId(@PathVariable long id, Model model) {

        Imagen imagen = imagenRepository.getReferenceById(id);

        byte[] encode = Base64.getEncoder().encode(imagen.getImagen());

        model.addAttribute("id", imagen.getId());

        model.addAttribute("imagen", new String(encode, StandardCharsets.UTF_8));

        return "MostrarImagen";
    }

    @GetMapping
    @Operation(summary = "Muestra la cantidad de imagenes")
    public String listarCantidadImagenes(Model model) {

        Long cantidadImagenes = imagenRepository.count();

        model.addAttribute("cantidad", cantidadImagenes);

        return "index";

    }

    @DeleteMapping("/eliminar/{id}")
    @Transactional
    @Operation(summary = "Elimina una imagen")
    public ResponseEntity eliminarImagen(@PathVariable long id) {

        Imagen imagen = imagenRepository.getReferenceById(id);

        imagenRepository.deleteById(id);

        return ResponseEntity.ok().body("La imagen con el id: " + imagen.getId() + " a sido borrado");
    }
}
