package frgp.utn.edu.ar;

import java.math.BigDecimal;
import java.util.List;

public class CarritoResponse {

    private Integer carritoId;
    private Integer usuarioId;
    private Integer vendedorId;
    private List<CarritoItemResponse> items;
    private BigDecimal total;

    public Integer getCarritoId() {
        return carritoId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public Integer getVendedorId() {
        return vendedorId;
    }

    public List<CarritoItemResponse> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        return total;
    }
}