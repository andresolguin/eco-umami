package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.CarritoAgregarRequest;
import com.ecommerce.backend.entity.Carrito;
import com.ecommerce.backend.entity.CarritoItem;
import com.ecommerce.backend.entity.CarritoItemId;
import com.ecommerce.backend.entity.Producto;
import com.ecommerce.backend.entity.Usuario;
import com.ecommerce.backend.repository.CarritoItemRepository;
import com.ecommerce.backend.repository.CarritoRepository;
import com.ecommerce.backend.repository.ProductoRepository;
import com.ecommerce.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.backend.dto.CarritoItemResponse;
import com.ecommerce.backend.dto.CarritoResponse;

import java.math.BigDecimal;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public CarritoService(
            CarritoRepository carritoRepository,
            CarritoItemRepository carritoItemRepository,
            UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository) {

        this.carritoRepository = carritoRepository;
        this.carritoItemRepository = carritoItemRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Carrito agregarProducto(CarritoAgregarRequest request) {

        if (request.getCantidad() == null || request.getCantidad() <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor a cero");
        }

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));

        if (!Boolean.TRUE.equals(producto.getEstado())) {
            throw new IllegalArgumentException(
                    "El producto no está disponible");
        }

        if (producto.getFechaVencimiento().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "El producto está vencido");
        }

        if (producto.getStock() < request.getCantidad()) {
            throw new IllegalArgumentException(
                    "Stock insuficiente");
        }

        Carrito carrito = carritoRepository
                .findByUsuarioId(usuario.getId())
                .orElse(null);

        if (carrito == null) {

            carrito = Carrito.builder()
                    .usuario(usuario)
                    .vendedor(producto.getVendedor())
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            carrito = carritoRepository.save(carrito);

        } else if (!carrito.getVendedor().getId()
                .equals(producto.getVendedor().getId())) {

            throw new IllegalArgumentException(
                    "El carrito solo puede contener productos del mismo comercio");
        }

        CarritoItemId itemId =
                new CarritoItemId(carrito.getId(), producto.getId());

        CarritoItem item = carritoItemRepository
                .findById(itemId)
                .orElse(null);

        if (item == null) {

            item = CarritoItem.builder()
                    .id(itemId)
                    .carrito(carrito)
                    .producto(producto)
                    .cantidad(request.getCantidad())
                    .build();

        } else {

            int nuevaCantidad =
                    item.getCantidad() + request.getCantidad();

            if (nuevaCantidad > producto.getStock()) {
                throw new IllegalArgumentException(
                        "Stock insuficiente");
            }

            item.setCantidad(nuevaCantidad);
        }

        carritoItemRepository.save(item);

        return carrito;
    }

    @Transactional(readOnly = true)
    public CarritoResponse obtenerCarrito(Integer idUsuario) {

        Carrito carrito = carritoRepository
                .findByUsuarioId(idUsuario)
                .orElse(null);

        if (carrito == null) {
            return CarritoResponse.builder()
                    .usuarioId(idUsuario)
                    .items(List.of())
                    .total(BigDecimal.ZERO)
                    .build();
        }

        List<CarritoItem> items =
                carritoItemRepository.findByCarritoId(carrito.getId());

        List<CarritoItemResponse> itemsResponse =
                items.stream()
                        .map(item -> {

                            BigDecimal precio =
                                    item.getProducto().getPrecioReducido();

                            BigDecimal subtotal =
                                    precio.multiply(
                                            BigDecimal.valueOf(
                                                    item.getCantidad()
                                            )
                                    );

                            return CarritoItemResponse.builder()
                                    .productoId(
                                            item.getProducto().getId()
                                    )
                                    .nombre(
                                            item.getProducto().getNombre()
                                    )
                                    .precio(precio)
                                    .cantidad(item.getCantidad())
                                    .subtotal(subtotal)
                                    .build();
                        })
                        .toList();

        BigDecimal total =
                itemsResponse.stream()
                        .map(CarritoItemResponse::getSubtotal)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        return CarritoResponse.builder()
                .carritoId(carrito.getId())
                .usuarioId(carrito.getUsuario().getId())
                .vendedorId(carrito.getVendedor().getId())
                .items(itemsResponse)
                .total(total)
                .build();
    }
}