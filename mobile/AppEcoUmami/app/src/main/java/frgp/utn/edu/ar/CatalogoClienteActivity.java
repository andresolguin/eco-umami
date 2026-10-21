package frgp.utn.edu.ar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogoClienteActivity extends AppCompatActivity {

    private RecyclerView recyclerProductos;
    private TextView txtEstadoCatalogo;
    private ProductoAdapter productoAdapter;
    private List<ProductoResponse> productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogocliente);

        recyclerProductos = findViewById(R.id.recyclerProductos);
        txtEstadoCatalogo = findViewById(R.id.txtEstadoCatalogo);

        productos = new ArrayList<>();

        recyclerProductos.setLayoutManager(
                new LinearLayoutManager(this)
        );

        productoAdapter = new ProductoAdapter(productos);
        recyclerProductos.setAdapter(productoAdapter);

        cargarProductos();
    }

    private void cargarProductos() {

        txtEstadoCatalogo.setVisibility(View.VISIBLE);
        txtEstadoCatalogo.setText("Cargando ofertas...");
        recyclerProductos.setVisibility(View.GONE);

        ProductoApi api = ApiClient.getClient()
                .create(ProductoApi.class);

        Call<List<ProductoResponse>> call =
                api.listarProductosActivos();

        call.enqueue(new Callback<List<ProductoResponse>>() {

            @Override
            public void onResponse(
                    Call<List<ProductoResponse>> call,
                    Response<List<ProductoResponse>> response) {

                if (isFinishing() || isDestroyed()) {
                    return;
                }

                if (response.isSuccessful() && response.body() != null) {

                    productos.clear();
                    productos.addAll(response.body());

                    productoAdapter.notifyDataSetChanged();

                    if (productos.isEmpty()) {
                        mostrarEstado("No se encontraron ofertas");
                    } else {
                        txtEstadoCatalogo.setVisibility(View.GONE);
                        recyclerProductos.setVisibility(View.VISIBLE);
                    }

                } else {
                    mostrarEstado(
                            "No se pudieron cargar las ofertas. " +
                                    "Código de error: " + response.code()
                    );
                }
            }

            @Override
            public void onFailure(
                    Call<List<ProductoResponse>> call,
                    Throwable t) {

                if (isFinishing() || isDestroyed()) {
                    return;
                }

                mostrarEstado(
                        "No se pudo conectar con el servidor. " +
                                "Verificá tu conexión e intentá nuevamente."
                );
            }
        });
    }

    private void mostrarEstado(String mensaje) {
        txtEstadoCatalogo.setText(mensaje);
        txtEstadoCatalogo.setVisibility(View.VISIBLE);
        recyclerProductos.setVisibility(View.GONE);
    }
}
