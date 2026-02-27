package com.ecommerce.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Producto {

    private int idProducto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precioOriginal;
    private BigDecimal precioReducido;
    private LocalDate fechaVencimiento;
    private int stock;
    private String unidadVenta;

    // relaciones
    private Categoria categoria;  // id_categoria
    private Usuario vendedor;      // id_vendedor

    private boolean estado;

    // getters y setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecioOriginal() { return precioOriginal; }
    public void setPrecioOriginal(BigDecimal precioOriginal) { this.precioOriginal = precioOriginal; }

    public BigDecimal getPrecioReducido() { return precioReducido; }
    public void setPrecioReducido(BigDecimal precioReducido) { this.precioReducido = precioReducido; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getUnidadVenta() { return unidadVenta; }
    public void setUnidadVenta(String unidadVenta) { this.unidadVenta = unidadVenta; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Usuario getVendedor() { return vendedor; }
    public void setVendedor(Usuario vendedor) { this.vendedor = vendedor; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}