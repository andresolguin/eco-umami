package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.*;
import com.ecommerce.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.backend.dto.PedidoItemResponse;
import com.ecommerce.backend.dto.PedidoResponse;

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
    private final UsuarioRepository usuarioRepository;

    public PedidoService(
            CarritoRepository carritoRepository,
            CarritoItemRepository carritoItemRepository,
            PedidoRepository pedidoRepository,
            PedidoItemRepository pedidoItemRepository,
            ProductoRepository productoRepository,
            UsuarioRepository usuarioRepository) {

        this.carritoRepository = carritoRepository;
        this.carritoItemRepository = carritoItemRepository;
        this.pedidoRepository = pedidoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
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

    @Transactional(readOnly = true)
    public List<PedidoResponse> obtenerPedidosPorUsuario(Integer idUsuario) {

        usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuario no encontrado"));

        return pedidoRepository
                .findByUsuarioIdOrderByFechaPedidoDesc(idUsuario)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> obtenerPedidosPorComercio(Integer idComercio) {

        usuarioRepository.findById(idComercio)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Comercio no encontrado"));

        return pedidoRepository
                .findByVendedorIdOrderByFechaPedidoDesc(idComercio)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    private PedidoResponse convertirAResponse(Pedido pedido) {

        List<PedidoItemResponse> items =
                pedidoItemRepository
                        .findByPedidoId(pedido.getId())
                        .stream()
                        .map(item ->
                                PedidoItemResponse.builder()
                                        .idProducto(
                                                item.getProducto().getId())
                                        .nombreProducto(
                                                item.getProducto().getNombre())
                                        .cantidad(
                                                item.getCantidad())
                                        .precio(
                                                item.getPrecio())
                                        .subtotal(
                                                item.getPrecio()
                                                        .multiply(
                                                                BigDecimal.valueOf(
                                                                        item.getCantidad())))
                                        .build()
                        )
                        .toList();

        return PedidoResponse.builder()
                .idPedido(pedido.getId())
                .fechaPedido(pedido.getFechaPedido())
                .fechaEntrega(pedido.getFechaEntrega())
                .precioTotal(pedido.getPrecioTotal())
                .metodoEntrega(pedido.getMetodoEntrega())
                .metodoPago(pedido.getMetodoPago())
                .estadoPago(pedido.getEstadoPago())
                .estadoPedido(pedido.getEstadoPedido())
                .idUsuario(pedido.getUsuario().getId())
                .nombreUsuario(
                        obtenerNombreUsuario(
                                pedido.getUsuario()))
                .idComercio(pedido.getVendedor().getId())
                .nombreComercio(
                        obtenerNombreComercio(
                                pedido.getVendedor()))
                .items(items)
                .build();
    }

    private String obtenerNombreUsuario(Usuario usuario) {

        String nombre = usuario.getNombre();
        String apellido = usuario.getApellido();

        if (nombre != null && apellido != null) {
            return nombre + " " + apellido;
        }

        if (nombre != null) {
            return nombre;
        }

        return usuario.getMail();
    }

    private String obtenerNombreComercio(Usuario comercio) {

        if (comercio.getRazonSocial() != null) {
            return comercio.getRazonSocial();
        }

        if (comercio.getNombre() != null) {
            return comercio.getNombre();
        }

        return comercio.getMail();
    }
}