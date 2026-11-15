package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.PedidoResponse;
import com.ecommerce.backend.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPedidosPorUsuario(
            @PathVariable Integer idUsuario) {

        try {
            List<PedidoResponse> pedidos =
                    pedidoService.obtenerPedidosPorUsuario(idUsuario);

            return ResponseEntity.ok(pedidos);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/comercio/{idComercio}")
    public ResponseEntity<?> obtenerPedidosPorComercio(
            @PathVariable Integer idComercio) {

        try {
            List<PedidoResponse> pedidos =
                    pedidoService.obtenerPedidosPorComercio(idComercio);

            return ResponseEntity.ok(pedidos);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}