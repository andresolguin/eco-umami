package com.ecommerce.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoCreateRequest {

    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precioOriginal;
    private BigDecimal precioReducido;
    private LocalDate fechaVencimiento;
    private Integer stock;
    private String unidadVenta;
    private Integer categoriaId;
    private Integer vendedorId;
}