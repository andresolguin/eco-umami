package com.ecommerce.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarritoResponse {

    private Integer carritoId;
    private Integer usuarioId;
    private Integer vendedorId;
    private List<CarritoItemResponse> items;
    private BigDecimal total;
}