package frgp.utn.edu.ar;

import java.math.BigDecimal;

public class ProductoResponse {

    private Integer id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precioOriginal;
    private BigDecimal precioReducido;
    private String fechaVencimiento;
    private Integer stock;
    private String unidadVenta;
    private Boolean estado;
    private Integer categoriaId;
    private Integer vendedorId;

    public Integer getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getPrecioOriginal() {
        return precioOriginal;
    }

    public BigDecimal getPrecioReducido() {
        return precioReducido;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public Integer getStock() {
        return stock;
    }

    public String getUnidadVenta() {
        return unidadVenta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public Integer getVendedorId() {
        return vendedorId;
    }
}