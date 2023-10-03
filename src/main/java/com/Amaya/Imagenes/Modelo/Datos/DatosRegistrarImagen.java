package com.Amaya.Imagenes.Modelo.Datos;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record DatosRegistrarImagen (

        @NotBlank
        MultipartFile imagen

){
}
