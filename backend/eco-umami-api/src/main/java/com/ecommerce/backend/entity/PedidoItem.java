package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pedido_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoItem {

    @EmbeddedId
    private PedidoItemId id;

    @ManyToOne
    @MapsId("idPedido")
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private BigDecimal precio;
}