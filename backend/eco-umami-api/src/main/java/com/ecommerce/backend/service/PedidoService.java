package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.*;
import com.ecommerce.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private static final int METODO_ENTREGA_RETIRO_COMERCIO = 1;
    private static final int METODO_PAGO_EN_COMERCIO = 1;
    private static final int ESTADO_PAGO_PENDIENTE = 1;
    private static final int ESTADO_PEDIDO_GENERADO = 1;

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;
    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(
            CarritoRepository carritoRepository,
            CarritoItemRepository carritoItemRepository,
            PedidoRepository pedidoRepository,
            PedidoItemRepository pedidoItemRepository,
            ProductoRepository productoRepository) {

        this.carritoRepository = carritoRepository;
        this.carritoItemRepository = carritoItemRepository;
        this.pedidoRepository = pedidoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Pedido confirmarCompra(Integer idUsuario) {

        Carrito carrito = carritoRepository
                .findByUsuarioId(idUsuario)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "El carrito está vacío"));

        List<CarritoItem> items =
                carritoItemRepository
                        .findByCarritoId(carrito.getId());

        if (items.isEmpty()) {
            throw new IllegalArgumentException(
                    "El carrito está vacío");
        }

        BigDecimal total = BigDecimal.ZERO;

        // Revalidamos disponibilidad y calculamos el total
        // justo antes de confirmar la compra.
        for (CarritoItem item : items) {

            Producto producto = item.getProducto();

            if (!Boolean.TRUE.equals(producto.getEstado())) {
                throw new IllegalArgumentException(
                        "El producto "
                                + producto.getNombre()
                                + " ya no está disponible");
            }

            if (producto.getFechaVencimiento()
                    .isBefore(LocalDate.now())) {

                throw new IllegalArgumentException(
                        "El producto "
                                + producto.getNombre()
                                + " está vencido");
            }

            if (producto.getStock() < item.getCantidad()) {

                throw new IllegalArgumentException(
                        "Stock insuficiente para "
                                + producto.getNombre());
            }

            BigDecimal subtotal =
                    producto.getPrecioReducido()
                            .multiply(
                                    BigDecimal.valueOf(
                                            item.getCantidad()));

            total = total.add(subtotal);
        }

        Pedido pedido = Pedido.builder()
                .usuario(carrito.getUsuario())
                .vendedor(carrito.getVendedor())
                .fechaPedido(LocalDateTime.now())
                .metodoEntrega(
                        METODO_ENTREGA_RETIRO_COMERCIO)
                .fechaEntrega(LocalDate.now())
                .precioTotal(total)
                .metodoPago(
                        METODO_PAGO_EN_COMERCIO)
                .estadoPago(
                        ESTADO_PAGO_PENDIENTE)
                .estadoPedido(
                        ESTADO_PEDIDO_GENERADO)
                .build();

        pedido = pedidoRepository.save(pedido);

        // Generamos los ítems del pedido y descontamos stock.
        for (CarritoItem item : items) {

            Producto producto = item.getProducto();

            PedidoItem pedidoItem =
                    PedidoItem.builder()
                            .id(new PedidoItemId(
                                    pedido.getId(),
                                    producto.getId()))
                            .pedido(pedido)
                            .producto(producto)
                            .cantidad(item.getCantidad())
                            .precio(
                                    producto.getPrecioReducido())
                            .build();

            pedidoItemRepository.save(pedidoItem);

            producto.setStock(
                    producto.getStock()
                            - item.getCantidad());

            productoRepository.save(producto);
        }

        // La compra ya fue convertida en pedido:
        // vaciamos el carrito.
        carritoItemRepository.deleteAll(items);
        carritoRepository.delete(carrito);

        return pedido;
    }
}