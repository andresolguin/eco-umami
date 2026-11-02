package frgp.utn.edu.ar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarritoActivity extends AppCompatActivity {

    private TextView txtCarritoVacio;
    private TextView txtTotalCarrito;
    private LinearLayout contenedorItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        txtCarritoVacio = findViewById(R.id.txtCarritoVacio);
        txtTotalCarrito = findViewById(R.id.txtTotalCarrito);
        contenedorItems = findViewById(R.id.contenedorItems);

        SharedPreferences preferences =
                getSharedPreferences(
                        "EcoUmamiPrefs",
                        MODE_PRIVATE
                );

        int idUsuario =
                preferences.getInt("idUsuario", -1);

        if (idUsuario == -1) {
            Toast.makeText(
                    this,
                    "No se pudo identificar al usuario",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }

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
                }
        );
    }

    private void mostrarCarrito(CarritoResponse carrito) {

        contenedorItems.removeAllViews();

        List<CarritoItemResponse> items =
                carrito.getItems();

        if (items == null || items.isEmpty()) {

            txtCarritoVacio.setVisibility(View.VISIBLE);
            txtTotalCarrito.setText("Total: $0");

            return;
        }

        txtCarritoVacio.setVisibility(View.GONE);

        for (CarritoItemResponse item : items) {

            TextView txtItem = new TextView(this);

            txtItem.setText(
                    item.getNombre()
                            + "\nCantidad: " + item.getCantidad()
                            + "\nPrecio: $" + item.getPrecio()
                            + "\nSubtotal: $" + item.getSubtotal()
            );

            txtItem.setTextSize(16);
            txtItem.setPadding(
                    0,
                    16,
                    0,
                    24
            );

            contenedorItems.addView(txtItem);
        }

        txtTotalCarrito.setText(
                "Total: $" + carrito.getTotal()
        );
    }
}