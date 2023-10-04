package com.Amaya.Imagenes.Repository;

import com.Amaya.Imagenes.Modelo.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {

    @Query("""
            select i.imagen from Imagen i
            """)
    List<byte[]> getAllImagen();
}
