package frgp.utn.edu.ar;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProductoApi {

    @POST("/api/productos")
    Call<Void> crearProducto(@Body ProductoRequest producto);

}