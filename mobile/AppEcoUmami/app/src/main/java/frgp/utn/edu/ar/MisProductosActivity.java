package frgp.utn.edu.ar;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MisProductosActivity extends AppCompatActivity {

    private LinearLayout contenedorProductos;
    private TextView txtMensaje;
    private ProductoApi api;
    private List<ProductoResponse> productos = new ArrayList<>();
    private Button btnVolverMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misproductos);

        contenedorProductos = findViewById(R.id.contenedorProductos);
        txtMensaje = findViewById(R.id.txtMensaje);

        btnVolverMenu = findViewById(R.id.btnVolverMenu);

        btnVolverMenu.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MisProductosActivity.this,
                    MenuNegocioActivity.class
            );
            startActivity(intent);
            finish();
        });

        api = ApiClient.getClient().create(ProductoApi.class);

        cargarProductos();
    }

    private void cargarProductos() {

        int idUsuario = getSharedPreferences(
                "EcoUmamiPrefs", MODE_PRIVATE
        ).getInt("idUsuario", -1);

        if (idUsuario == -1) {
            txtMensaje.setText("No se pudo identificar al comercio.");
            return;
        }

        txtMensaje.setText("Cargando tus productos...");
        contenedorProductos.removeAllViews();

        api.listarProductosVendedor(idUsuario).enqueue(
                new Callback<List<ProductoResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<ProductoResponse>> call,
                            Response<List<ProductoResponse>> response) {

                        if (!response.isSuccessful() || response.body() == null) {
                            txtMensaje.setText(
                                    "No se pudieron cargar los productos."
                            );
                            return;
                        }

                        productos.clear();

                        // Solamente guardar los productos activos.
                        for (ProductoResponse p : response.body()) {
                            if (Boolean.TRUE.equals(p.getEstado())) {
                                productos.add(p);
                            }
                        }

                        contenedorProductos.removeAllViews();

                        if (productos.isEmpty()) {
                            txtMensaje.setText(
                                    "Todavía no tenés productos activos.\n" +
                                            "¡Publicá uno nuevo desde el menú!"
                            );
                            return;
                        }

                        txtMensaje.setText(
                                "Tenés " + productos.size() +
                                        " producto(s) activo(s)."
                        );

                        for (ProductoResponse p : productos) {
                            agregarTarjeta(p);
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<ProductoResponse>> call,
                            Throwable t) {

                        txtMensaje.setText(
                                "Error de conexión con el servidor.\n" +
                                        "Verificá tu conexión e intentá nuevamente."
                        );
                    }
                }
        );
    }

    private void agregarTarjeta(ProductoResponse producto) {

        // Tarjeta principal.
        CardView tarjeta = new CardView(this);
        LinearLayout.LayoutParams parametrosTarjeta =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosTarjeta.setMargins(0, 0, 0, 20);

        tarjeta.setLayoutParams(parametrosTarjeta);
        tarjeta.setRadius(18);
        tarjeta.setCardElevation(4);
        tarjeta.setCardBackgroundColor(Color.WHITE);

        // Contenedor interno.
        LinearLayout contenido = new LinearLayout(this);
        contenido.setOrientation(LinearLayout.VERTICAL);
        contenido.setPadding(24, 20, 24, 20);

        // Nombre.
        TextView nombre = new TextView(this);
        nombre.setText(producto.getNombre());
        nombre.setTextSize(19);
        nombre.setTextColor(Color.rgb(46, 125, 50));
        nombre.setTypeface(null, Typeface.BOLD);

        contenido.addView(nombre);

        // Descripción.
        TextView descripcion = new TextView(this);
        descripcion.setText(producto.getDescripcion());
        descripcion.setTextSize(14);
        descripcion.setTextColor(Color.DKGRAY);
        descripcion.setPadding(0, 8, 0, 12);

        contenido.addView(descripcion);

        // Precio original.
        TextView precioOriginal = new TextView(this);
        precioOriginal.setText(
                "Precio original: $" + producto.getPrecioOriginal()
        );
        precioOriginal.setTextSize(14);
        precioOriginal.setTextColor(Color.GRAY);

        contenido.addView(precioOriginal);

        // Precio reducido.
        TextView precioReducido = new TextView(this);
        precioReducido.setText(
                "Precio de oferta: $" + producto.getPrecioReducido()
        );
        precioReducido.setTextSize(17);
        precioReducido.setTextColor(Color.rgb(46, 125, 50));
        precioReducido.setTypeface(null, Typeface.BOLD);
        precioReducido.setPadding(0, 6, 0, 6);

        contenido.addView(precioReducido);

        // Stock.
        TextView stock = new TextView(this);
        stock.setText("Stock disponible: " + producto.getStock());
        stock.setTextSize(14);
        stock.setTextColor(Color.DKGRAY);

        contenido.addView(stock);

        // Botón para dar de baja.
        Button botonBaja = new Button(this);
        botonBaja.setText("Dar de baja");
        botonBaja.setTextColor(Color.WHITE);
        botonBaja.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(
                        Color.rgb(198, 40, 40)
                )
        );

        LinearLayout.LayoutParams parametrosBoton =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosBoton.setMargins(0, 14, 0, 0);
        botonBaja.setLayoutParams(parametrosBoton);

        botonBaja.setOnClickListener(v ->
                confirmarBaja(producto)
        );

        contenido.addView(botonBaja);

        tarjeta.addView(contenido);
        contenedorProductos.addView(tarjeta);
    }

    private void confirmarBaja(ProductoResponse producto) {

        new AlertDialog.Builder(this)
                .setTitle("Dar de baja producto")
                .setMessage(
                        "¿Estás seguro de que querés dar de baja \"" +
                                producto.getNombre() + "\"?\n\n" +
                                "Dejará de aparecer en el catálogo de clientes."
                )
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Dar de baja",
                        (dialog, which) -> darDeBaja(producto))
                .show();
    }

    private void darDeBaja(ProductoResponse producto) {

        api.eliminarProducto(producto.getId()).enqueue(
                new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response) {

                        if (response.isSuccessful()) {

                            productos.remove(producto);

                            // Eliminar todas las tarjetas y reconstruirlas.
                            contenedorProductos.removeAllViews();

                            for (ProductoResponse p : productos) {
                                agregarTarjeta(p);
                            }

                            Toast.makeText(
                                    MisProductosActivity.this,
                                    "Producto dado de baja correctamente",
                                    Toast.LENGTH_SHORT
                            ).show();

                            if (productos.isEmpty()) {
                                txtMensaje.setText(
                                        "Ya no tenés productos activos.\n" +
                                                "¡Podés publicar nuevos desde el menú!"
                                );
                            } else {
                                txtMensaje.setText(
                                        "Tenés " + productos.size() +
                                                " producto(s) activo(s)."
                                );
                            }

                        } else {
                            Toast.makeText(
                                    MisProductosActivity.this,
                                    "No se pudo dar de baja el producto",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(
                                MisProductosActivity.this,
                                "Error de conexión con el servidor",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }
}
