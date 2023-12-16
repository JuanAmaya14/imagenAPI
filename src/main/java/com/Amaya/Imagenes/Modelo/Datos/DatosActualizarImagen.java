package com.Amaya.Imagenes.Modelo.Datos;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarImagen(

        @NotNull(message = "El id no puede ser vacio")
        long id,

        byte[] imagen
) {
}
