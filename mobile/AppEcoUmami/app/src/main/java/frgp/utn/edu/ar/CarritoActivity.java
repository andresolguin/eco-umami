package frgp.utn.edu.ar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarritoActivity extends AppCompatActivity {

    private TextView txtCarritoVacio;
    private TextView txtTotalCarrito;
    private LinearLayout contenedorItems;
    private Button btnConfirmarCompra;

    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        txtCarritoVacio = findViewById(R.id.txtCarritoVacio);
        txtTotalCarrito = findViewById(R.id.txtTotalCarrito);
        contenedorItems = findViewById(R.id.contenedorItems);
        btnConfirmarCompra = findViewById(R.id.btnConfirmarCompra);

        SharedPreferences preferences =
                getSharedPreferences("EcoUmamiPrefs", MODE_PRIVATE);

        idUsuario = preferences.getInt("idUsuario", -1);

        if (idUsuario == -1) {
            Toast.makeText(
                    this,
                    "No se pudo identificar al usuario",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        btnConfirmarCompra.setOnClickListener(v ->
                confirmarCompra()
        );

        cargarCarrito(idUsuario);
    }

    private void cargarCarrito(int idUsuario) {

        CarritoApi api =
                ApiClient.getClient().create(CarritoApi.class);

        api.obtenerCarrito(idUsuario).enqueue(
                new Callback<CarritoResponse>() {

                    @Override
                    public void onResponse(
                            Call<CarritoResponse> call,
                            Response<CarritoResponse> response) {

                        if (!response.isSuccessful()
                                || response.body() == null) {

                            Toast.makeText(
                                    CarritoActivity.this,
                                    "No se pudo cargar el carrito",
                                    Toast.LENGTH_LONG
                            ).show();

                            return;
                        }

                        mostrarCarrito(response.body());
                    }

                    @Override
                    public void onFailure(
                            Call<CarritoResponse> call,
                            Throwable t) {

                        Toast.makeText(
                                CarritoActivity.this,
                                "No se pudo conectar con el servidor",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    private void mostrarCarrito(CarritoResponse carrito) {

        contenedorItems.removeAllViews();

        List<CarritoItemResponse> items =
                carrito.getItems();

        if (items == null || items.isEmpty()) {

            txtCarritoVacio.setVisibility(View.VISIBLE);
            txtTotalCarrito.setText("Total: $0");
            btnConfirmarCompra.setEnabled(false);

            return;
        }

        txtCarritoVacio.setVisibility(View.GONE);
        btnConfirmarCompra.setEnabled(true);

        for (CarritoItemResponse item : items) {

            LinearLayout bloqueItem =
                    new LinearLayout(this);

            bloqueItem.setOrientation(
                    LinearLayout.VERTICAL
            );

            TextView txtItem =
                    new TextView(this);

            txtItem.setText(
                    item.getNombre()
                            + "\nCantidad: "
                            + item.getCantidad()
                            + "\nPrecio: $"
                            + item.getPrecio()
                            + "\nSubtotal: $"
                            + item.getSubtotal()
            );

            txtItem.setTextSize(16);
            txtItem.setPadding(
                    0,
                    16,
                    0,
                    8
            );

            Button btnEliminar =
                    new Button(this);

            btnEliminar.setText("Eliminar");

            btnEliminar.setOnClickListener(v ->
                    eliminarProducto(
                            item.getProductoId()
                    )
            );

            bloqueItem.addView(txtItem);
            bloqueItem.addView(btnEliminar);

            bloqueItem.setPadding(
                    0,
                    0,
                    0,
                    24
            );

            contenedorItems.addView(
                    bloqueItem
            );
        }

        txtTotalCarrito.setText(
                "Total: $" + carrito.getTotal()
        );
    }

    private void eliminarProducto(Integer idProducto) {

        CarritoApi api =
                ApiClient.getClient().create(CarritoApi.class);

        api.eliminarProducto(
                idUsuario,
                idProducto
        ).enqueue(
                new Callback<Map<String, String>>() {

                    @Override
                    public void onResponse(
                            Call<Map<String, String>> call,
                            Response<Map<String, String>> response) {

                        if (!response.isSuccessful()) {

                            Toast.makeText(
                                    CarritoActivity.this,
                                    "No se pudo eliminar el producto",
                                    Toast.LENGTH_LONG
                            ).show();

                            return;
                        }

                        Toast.makeText(
                                CarritoActivity.this,
                                "Producto eliminado del carrito",
                                Toast.LENGTH_SHORT
                        ).show();

                        cargarCarrito(idUsuario);
                    }

                    @Override
                    public void onFailure(
                            Call<Map<String, String>> call,
                            Throwable t) {

                        Toast.makeText(
                                CarritoActivity.this,
                                "No se pudo conectar con el servidor",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    private void confirmarCompra() {

        btnConfirmarCompra.setEnabled(false);

        CarritoApi api =
                ApiClient.getClient().create(CarritoApi.class);

        api.confirmarCompra(idUsuario).enqueue(
                new Callback<Map<String, Object>>() {

                    @Override
                    public void onResponse(
                            Call<Map<String, Object>> call,
                            Response<Map<String, Object>> response) {

                        if (!response.isSuccessful()
                                || response.body() == null) {

                            btnConfirmarCompra.setEnabled(true);

                            Toast.makeText(
                                    CarritoActivity.this,
                                    "No se pudo confirmar la compra",
                                    Toast.LENGTH_LONG
                            ).show();

                            return;
                        }

                        Object idPedido =
                                response.body().get("idPedido");

                        Toast.makeText(
                                CarritoActivity.this,
                                "Compra confirmada. Pedido N° "
                                        + idPedido,
                                Toast.LENGTH_LONG
                        ).show();

                        cargarCarrito(idUsuario);
                    }

                    @Override
                    public void onFailure(
                            Call<Map<String, Object>> call,
                            Throwable t) {

                        btnConfirmarCompra.setEnabled(true);

                        Toast.makeText(
                                CarritoActivity.this,
                                "No se pudo conectar con el servidor",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}