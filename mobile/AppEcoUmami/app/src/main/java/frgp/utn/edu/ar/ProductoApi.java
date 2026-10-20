package frgp.utn.edu.ar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductoApi {

    @POST("/api/productos")
    Call<Void> crearProducto(@Body ProductoRequest producto);

    @GET("api/productos/activos")
    Call<List<ProductoResponse>> listarProductosActivos();
}
