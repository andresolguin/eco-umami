package com.ecommerce.backend.controller;

import com.ecommerce.backend.entity.Imagen;
import com.ecommerce.backend.service.ImagenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/imagenes")
public class ImagenController {

    private final ImagenService imagenService;

    public ImagenController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    // 🔹 LISTAR TODAS
    @GetMapping
    public List<Imagen> listarTodos(){
        return imagenService.listarTodos();
    }

    // 🔹 BUSCAR POR ID
    @GetMapping("/{id}")
    public Optional<Imagen> buscarPorId(@PathVariable Integer id){
        return imagenService.buscarPorId(id);
    }

    // 🔹 CREAR
    @PostMapping
    public Imagen guardar(@RequestBody Imagen imagen){
        return imagenService.guardar(imagen);
    }

    // 🔹 MODIFICAR
    @PutMapping("/{id}")
    public Imagen modificar(@PathVariable Integer id, @RequestBody Imagen imagen){
        return imagenService.modificar(id, imagen);
    }

    // 🔹 ELIMINACIÓN FÍSICA
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        imagenService.eliminar(id);
    }

    // 🔹 BUSCAR POR PRODUCTO
    @GetMapping("/producto/{idProducto}")
    public List<Imagen> buscarPorProducto(@PathVariable Integer idProducto){
        return imagenService.buscarPorProducto(idProducto);
    }
}