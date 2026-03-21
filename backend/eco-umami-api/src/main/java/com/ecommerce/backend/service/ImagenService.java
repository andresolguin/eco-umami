package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Imagen;
import com.ecommerce.backend.repository.ImagenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenService {

    private final ImagenRepository imagenRepository;

    public ImagenService(ImagenRepository imagenRepository) {
        this.imagenRepository = imagenRepository;
    }

    // 🔹 LISTAR TODAS
    public List<Imagen> listarTodos(){
        return imagenRepository.findAll();
    }

    // 🔹 BUSCAR POR ID
    public Optional<Imagen> buscarPorId(Integer id){
        return imagenRepository.findById(id);
    }

    // 🔹 GUARDAR
    public Imagen guardar(Imagen imagen){
        return imagenRepository.save(imagen);
    }

    // 🔹 MODIFICAR
    public Imagen modificar(Integer id, Imagen imagen){
        Imagen existente = imagenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada"));

        existente.setImagenUrl(imagen.getImagenUrl());
        existente.setProducto(imagen.getProducto());

        return imagenRepository.save(existente);
    }

    // 🔹 ELIMINACIÓN FÍSICA
    public void eliminar(Integer id){
        if (!imagenRepository.existsById(id)) {
            throw new RuntimeException("Imagen no encontrada");
        }
        imagenRepository.deleteById(id);
    }

    // 🔹 BUSCAR POR PRODUCTO
    public List<Imagen> buscarPorProducto(Integer idProducto){
        return imagenRepository.findByProductoId(idProducto);
    }
}