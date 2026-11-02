package frgp.utn.edu.ar;

public class CarritoAgregarRequest {

    private Integer idUsuario;
    private Integer idProducto;
    private Integer cantidad;

    public CarritoAgregarRequest(
            Integer idUsuario,
            Integer idProducto,
            Integer cantidad) {

        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }
}