package frgp.utn.edu.ar;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.DELETE;

public interface CarritoApi {

    @POST("api/carrito/items")
    Call<Map<String, Object>> agregarProducto(
            @Body CarritoAgregarRequest request
    );

    @GET("api/carrito/usuario/{idUsuario}")
    Call<CarritoResponse> obtenerCarrito(
            @Path("idUsuario") Integer idUsuario
    );
    @DELETE("api/carrito/usuario/{idUsuario}/items/{idProducto}")
    Call<Map<String, String>> eliminarProducto(
            @Path("idUsuario") Integer idUsuario,
            @Path("idProducto") Integer idProducto
    );
    @POST("api/carrito/usuario/{idUsuario}/confirmar")
    Call<Map<String, Object>> confirmarCompra(
            @Path("idUsuario") Integer idUsuario
    );
}