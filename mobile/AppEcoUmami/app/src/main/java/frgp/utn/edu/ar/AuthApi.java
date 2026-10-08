package frgp.utn.edu.ar;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("api/auth/login")
    Call<Usuario> login(@Body LoginRequest request);

    @POST("api/auth/register")
    Call<Usuario> register(@Body RegisterRequest request);
}