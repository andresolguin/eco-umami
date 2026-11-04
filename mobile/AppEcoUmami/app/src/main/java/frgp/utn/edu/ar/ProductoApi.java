package frgp.utn.edu.ar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductoApi {

    @POST("/api/productos")
    Call<Void> crearProducto(@Body ProductoRequest producto);

    @GET("api/productos/activos")
    Call<List<ProductoResponse>> listarProductosActivos();

    @GET("api/productos/{id}")
    Call<ProductoResponse> obtenerProductoPorId(@Path("id") Integer id);

    @GET("api/productos/vendedor/{idVendedor}")
    Call<List<ProductoResponse>> listarProductosVendedor(
            @Path("idVendedor") Integer idVendedor
    );

    @DELETE("api/productos/{id}")
    Call<Void> eliminarProducto(@Path("id") Integer id);
}

