package com.ecommerce.backend.controller;

import com.ecommerce.backend.entity.Producto;
import com.ecommerce.backend.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 🔹 LISTAR TODOS
    @GetMapping
    public List<Producto> listarTodos(){
        return productoService.listarTodos();
    }

    // 🔹 LISTAR ACTIVOS
    @GetMapping("/activos")
    public List<Producto> listarActivos(){
        return productoService.listarActivos();
    }

    // 🔹 LISTAR POR ESTADO
    @GetMapping("/estado")
    public List<Producto> listarPorEstado(@RequestParam Boolean estado){
        return productoService.listarPorEstado(estado);
    }

    // 🔹 BUSCAR POR ID
    @GetMapping("/{id}")
    public Optional<Producto> buscarPorId(@PathVariable Integer id){
        return productoService.buscarPorId(id);
    }

    // 🔹 CREAR
    @PostMapping
    public Producto guardar(@RequestBody Producto producto){
        return productoService.guardar(producto);
    }

    // 🔹 MODIFICAR
    @PutMapping("/{id}")
    public Producto modificar(@PathVariable Integer id, @RequestBody Producto producto){
        return productoService.modificar(id, producto);
    }

    // 🔹 ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        productoService.eliminar(id);
    }

    // 🔹 REACTIVAR
    @PutMapping("/reactivar/{id}")
    public void reactivar(@PathVariable Integer id){
        productoService.reactivar(id);
    }

    // 🔹 BUSCAR POR NOMBRE
    @GetMapping("/buscar")
    public List<Producto> buscarPorNombre(@RequestParam String nombre){
        return productoService.buscarPorNombre(nombre);
    }

    // 🔹 BUSCAR POR CATEGORÍA
    @GetMapping("/categoria/{idCategoria}")
    public List<Producto> buscarPorCategoria(@PathVariable Integer idCategoria){
        return productoService.buscarPorCategoria(idCategoria);
    }

    // 🔹 BUSCAR POR CÓDIGO
    @GetMapping("/codigo")
    public Optional<Producto> buscarPorCodigo(@RequestParam String codigo){
        return productoService.buscarPorCodigo(codigo);
    }
}