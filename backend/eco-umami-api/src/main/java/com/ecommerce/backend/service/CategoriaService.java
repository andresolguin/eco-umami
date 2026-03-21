package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Categoria;
import com.ecommerce.backend.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // 🔹 LISTAR TODOS
    public List<Categoria> listarTodos(){
        return categoriaRepository.findAll();
    }

    // 🔹 LISTAR ACTIVOS
    public List<Categoria> listarActivos(){
        return categoriaRepository.findByEstadoTrue();
    }

    // 🔹 LISTAR POR ESTADO
    public List<Categoria> listarPorEstado(Boolean estado){
        return categoriaRepository.findByEstado(estado);
    }

    // 🔹 BUSCAR POR ID
    public Optional<Categoria> buscarPorId(Integer id){
        return categoriaRepository.findById(id);
    }

    // 🔹 GUARDAR / CREAR
    public Categoria guardar(Categoria categoria){
        if (categoria.getEstado() == null) {
            categoria.setEstado(true);
        }
        return categoriaRepository.save(categoria);
    }

    // 🔹 MODIFICAR
    public Categoria modificar(Integer id, Categoria categoria){
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        existente.setDescripcion(categoria.getDescripcion());

        return categoriaRepository.save(existente);
    }

    // 🔹 ELIMINACIÓN LÓGICA
    public void eliminar(Integer id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.setEstado(false);
        categoriaRepository.save(categoria);
    }

    // 🔹 REACTIVAR
    public void reactivar(Integer id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.setEstado(true);
        categoriaRepository.save(categoria);
    }

    // 🔹 BUSCAR POR DESCRIPCIÓN
    public List<Categoria> buscarPorDescripcion(String descripcion){
        return categoriaRepository.findByDescripcionContainingIgnoreCase(descripcion);
    }
}