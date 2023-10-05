package com.Amaya.Imagenes.Controller;

import com.Amaya.Imagenes.Modelo.Datos.DatosRespuestaImagen;
import com.Amaya.Imagenes.Modelo.Imagen;
import com.Amaya.Imagenes.Repository.ImagenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Base64;
import java.util.List;

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

    @GetMapping("/imagen/{id}")
    public String listarImagenPorId(@PathVariable long id, Model model) throws IOException {

        Imagen imagen = imagenRepository.getReferenceById(id);

        byte[] encode = Base64.getEncoder().encode(imagen.getImagen());

        model.addAttribute("id", imagen.getId());

        model.addAttribute("imagen", new String(encode, "UTF-8"));

        return "MostrarImagen";
    }

//    @GetMapping("/")
//    public String listarImagenes(Model model) {
//
//        List<byte[]> imagenes = imagenRepository.getAllImagen();
//
//        model.addAttribute("imagenes", imagenes);
//
//        return "imagenes";
//
//    }

    @GetMapping("/")
    public String listarImagenes(Model model) throws UnsupportedEncodingException {

        List<Imagen> imagenes = imagenRepository.findAll();

        byte[] encode = Base64.getEncoder().encode(imagenes.get().getImagen());

        model.addAttribute("images", new String(encode, "UTF-8"));

        return "imagenes";

    }

    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity eliminarImagen(@PathVariable long id) {


        return ResponseEntity.ok().body("");
    }
}
