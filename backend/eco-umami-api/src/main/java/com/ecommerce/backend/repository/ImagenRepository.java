package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenRepository extends JpaRepository<Imagen, Integer> {

    List<Imagen> findByProductoId(Integer idProducto);
}