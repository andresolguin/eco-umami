package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.CarritoAgregarRequest;
import com.ecommerce.backend.dto.CarritoResponse;
import com.ecommerce.backend.entity.Carrito;
import com.ecommerce.backend.service.CarritoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping({"/carrito", "/api/carrito"})
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @PostMapping("/items")
    public ResponseEntity<Map<String, Object>> agregarProducto(
            @RequestBody CarritoAgregarRequest request) {

        Carrito carrito = carritoService.agregarProducto(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "mensaje", "Producto agregado al carrito",
                        "idCarrito", carrito.getId()
                ));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<CarritoResponse> obtenerCarrito(
            @PathVariable Integer idUsuario) {

        CarritoResponse carrito =
                carritoService.obtenerCarrito(idUsuario);

        return ResponseEntity.ok(carrito);
    }
}