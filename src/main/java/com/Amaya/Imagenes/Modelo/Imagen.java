package com.Amaya.Imagenes.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Table(name = "imagenes")
@Entity(name = "Imagen")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private byte[] imagen;

    public Imagen(MultipartFile multipartFile) throws IOException {

        this.imagen = multipartFile.getBytes();
    }
}
