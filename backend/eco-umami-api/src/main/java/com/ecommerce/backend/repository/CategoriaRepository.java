package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByEstadoTrue();

    List<Categoria> findByEstado(Boolean estado);

    List<Categoria> findByDescripcionContainingIgnoreCase(String descripcion);
}