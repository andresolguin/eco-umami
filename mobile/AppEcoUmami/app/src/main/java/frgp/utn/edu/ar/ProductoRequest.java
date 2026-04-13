package frgp.utn.edu.ar;

import java.math.BigDecimal;

public class ProductoRequest {

    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precioOriginal;
    private BigDecimal precioReducido;
    private String fechaVencimiento;
    private Integer stock;
    private String unidadVenta;
    private Integer categoriaId;
    private Integer vendedorId;

    // 🔹 GETTERS Y SETTERS

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

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getUnidadVenta() { return unidadVenta; }
    public void setUnidadVenta(String unidadVenta) { this.unidadVenta = unidadVenta; }

    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }

    public Integer getVendedorId() { return vendedorId; }
    public void setVendedorId(Integer vendedorId) { this.vendedorId = vendedorId; }
}
