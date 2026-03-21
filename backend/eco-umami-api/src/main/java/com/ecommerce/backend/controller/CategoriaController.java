package com.ecommerce.backend.controller;

import com.ecommerce.backend.entity.Categoria;
import com.ecommerce.backend.service.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // 🔹 LISTAR TODOS
    @GetMapping
    public List<Categoria> listarTodos(){
        return categoriaService.listarTodos();
    }

    // 🔹 LISTAR ACTIVOS
    @GetMapping("/activos")
    public List<Categoria> listarActivos(){
        return categoriaService.listarActivos();
    }

    // 🔹 LISTAR POR ESTADO
    @GetMapping("/estado")
    public List<Categoria> listarPorEstado(@RequestParam Boolean estado){
        return categoriaService.listarPorEstado(estado);
    }

    // 🔹 BUSCAR POR ID
    @GetMapping("/{id}")
    public Optional<Categoria> buscarPorId(@PathVariable Integer id){
        return categoriaService.buscarPorId(id);
    }

    // 🔹 CREAR
    @PostMapping
    public Categoria guardar(@RequestBody Categoria categoria){
        return categoriaService.guardar(categoria);
    }

    // 🔹 MODIFICAR
    @PutMapping("/{id}")
    public Categoria modificar(@PathVariable Integer id, @RequestBody Categoria categoria){
        return categoriaService.modificar(id, categoria);
    }

    // 🔹 ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        categoriaService.eliminar(id);
    }

    // 🔹 REACTIVAR
    @PutMapping("/reactivar/{id}")
    public void reactivar(@PathVariable Integer id){
        categoriaService.reactivar(id);
    }

    // 🔹 BUSCAR POR DESCRIPCIÓN
    @GetMapping("/buscar")
    public List<Categoria> buscarPorDescripcion(@RequestParam String descripcion){
        return categoriaService.buscarPorDescripcion(descripcion);
    }
}