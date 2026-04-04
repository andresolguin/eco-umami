package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ProductoCreateRequest;
import com.ecommerce.backend.dto.ProductoResponse;
import com.ecommerce.backend.entity.Categoria;
import com.ecommerce.backend.entity.Producto;
import com.ecommerce.backend.entity.Usuario;
import com.ecommerce.backend.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/productos", "/api/productos"})
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 🔹 LISTAR TODOS para admin
    @GetMapping
    public List<Producto> listarTodos(){
        return productoService.listarTodos();
    }

    // 🔹 LISTAR ACTIVOS para cliente
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
    public ResponseEntity<ProductoResponse> guardar(@RequestBody ProductoCreateRequest request) {

        Categoria categoria = new Categoria();
        categoria.setId(request.getCategoriaId());

        Usuario vendedor = new Usuario();
        vendedor.setId(request.getVendedorId());

        Producto producto = Producto.builder()
                .codigo(request.getCodigo())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .precioOriginal(request.getPrecioOriginal())
                .precioReducido(request.getPrecioReducido())
                .fechaVencimiento(request.getFechaVencimiento())
                .stock(request.getStock())
                .unidadVenta(request.getUnidadVenta())
                .estado(true)
                .categoria(categoria)
                .vendedor(vendedor)
                .build();

        Producto productoGuardado = productoService.guardar(producto);

        ProductoResponse response = ProductoResponse.builder()
                .id(productoGuardado.getId())
                .codigo(productoGuardado.getCodigo())
                .nombre(productoGuardado.getNombre())
                .descripcion(productoGuardado.getDescripcion())
                .precioOriginal(productoGuardado.getPrecioOriginal())
                .precioReducido(productoGuardado.getPrecioReducido())
                .fechaVencimiento(productoGuardado.getFechaVencimiento())
                .stock(productoGuardado.getStock())
                .unidadVenta(productoGuardado.getUnidadVenta())
                .estado(productoGuardado.getEstado())
                .categoriaId(productoGuardado.getCategoria().getId())
                .vendedorId(productoGuardado.getVendedor().getId())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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