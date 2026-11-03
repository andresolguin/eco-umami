package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.CarritoAgregarRequest;
import com.ecommerce.backend.dto.CarritoResponse;
import com.ecommerce.backend.entity.Carrito;
import com.ecommerce.backend.service.CarritoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.backend.entity.Pedido;
import com.ecommerce.backend.service.PedidoService;
import java.util.Map;

@RestController
@RequestMapping({"/carrito", "/api/carrito"})
public class CarritoController {

    private final CarritoService carritoService;
    private final PedidoService pedidoService;

    public CarritoController(
            CarritoService carritoService,
            PedidoService pedidoService) {

        this.carritoService = carritoService;
        this.pedidoService = pedidoService;
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
    @DeleteMapping("/usuario/{idUsuario}/items/{idProducto}")
    public ResponseEntity<Map<String, String>> eliminarProducto(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idProducto) {

        carritoService.eliminarProducto(idUsuario, idProducto);

        return ResponseEntity.ok(
                Map.of("mensaje", "Producto eliminado del carrito")
        );
    }
    @PostMapping("/usuario/{idUsuario}/confirmar")
    public ResponseEntity<Map<String, Object>> confirmarCompra(
            @PathVariable Integer idUsuario) {

        Pedido pedido =
                pedidoService.confirmarCompra(idUsuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "mensaje", "Compra confirmada correctamente",
                        "idPedido", pedido.getId(),
                        "total", pedido.getPrecioTotal()
                ));
    }
}