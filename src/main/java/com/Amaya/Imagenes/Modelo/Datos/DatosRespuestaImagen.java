package com.Amaya.Imagenes.Modelo.Datos;

import org.springframework.web.multipart.MultipartFile;

public record DatosRespuestaImagen (

        long id,
        byte[] imagen

){

}
