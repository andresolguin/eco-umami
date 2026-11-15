package com.ecommerce.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponse {

    private Integer idPedido;

    private LocalDateTime fechaPedido;
    private LocalDate fechaEntrega;

    private BigDecimal precioTotal;

    private Integer metodoEntrega;
    private Integer metodoPago;
    private Integer estadoPago;
    private Integer estadoPedido;

    private Integer idUsuario;
    private String nombreUsuario;

    private Integer idComercio;
    private String nombreComercio;

    private List<PedidoItemResponse> items;
}