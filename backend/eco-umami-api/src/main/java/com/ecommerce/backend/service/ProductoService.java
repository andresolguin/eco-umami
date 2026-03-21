package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Producto;
import com.ecommerce.backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // 🔹 LISTAR TODOS
    public List<Producto> listarTodos(){
        return productoRepository.findAll();
    }

    // 🔹 LISTAR SOLO ACTIVOS
    public List<Producto> listarActivos(){
        return productoRepository.findByEstadoTrue();
    }

    // 🔹 LISTAR POR ESTADO
    public List<Producto> listarPorEstado(Boolean estado){
        return productoRepository.findByEstado(estado);
    }

    // 🔹 BUSCAR POR ID
    public Optional<Producto> buscarPorId(Integer id){
        return productoRepository.findById(id);
    }

    // 🔹 AGREGAR / GUARDAR
    public Producto guardar(Producto producto){
        if (producto.getEstado() == null) {
            producto.setEstado(true);
        }
        return productoRepository.save(producto);
    }

    // 🔹 MODIFICAR
    public Producto modificar(Integer id, Producto producto){
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        existente.setCodigo(producto.getCodigo());
        existente.setNombre(producto.getNombre());
        existente.setDescripcion(producto.getDescripcion());
        existente.setPrecioOriginal(producto.getPrecioOriginal());
        existente.setPrecioReducido(producto.getPrecioReducido());
        existente.setFechaVencimiento(producto.getFechaVencimiento());
        existente.setStock(producto.getStock());
        existente.setUnidadVenta(producto.getUnidadVenta());
        existente.setCategoria(producto.getCategoria());
        existente.setVendedor(producto.getVendedor());

        return productoRepository.save(existente);
    }

    // 🔹 ELIMINACIÓN LÓGICA
    public void eliminar(Integer id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setEstado(false);
        productoRepository.save(producto);
    }

    // 🔹 REACTIVAR
    public void reactivar(Integer id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setEstado(true);
        productoRepository.save(producto);
    }

    // 🔹 BUSCAR POR NOMBRE
    public List<Producto> buscarPorNombre(String nombre){
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // 🔹 BUSCAR POR CATEGORÍA
    public List<Producto> buscarPorCategoria(Integer idCategoria){
        return productoRepository.findByCategoriaId(idCategoria);
    }

    // 🔹 BUSCAR POR CÓDIGO
    public Optional<Producto> buscarPorCodigo(String codigo){
        return productoRepository.findByCodigo(codigo);
    }
}