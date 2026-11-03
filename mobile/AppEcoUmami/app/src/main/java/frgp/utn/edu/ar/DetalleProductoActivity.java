package frgp.utn.edu.ar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleProductoActivity extends AppCompatActivity {

    private TextView txtNombreProducto;
    private TextView txtDescripcionProducto;
    private TextView txtPrecioReducido;
    private TextView txtPrecioOriginal;
    private TextView txtVencimiento;
    private TextView txtStock;
    private TextView txtUnidadVenta;
    private TextView txtCategoria;
    private TextView txtComercio;

    private EditText txtCantidad;
    private Button btnAgregarCarrito;

    private int productoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        txtNombreProducto = findViewById(R.id.txtNombreProducto);
        txtDescripcionProducto = findViewById(R.id.txtDescripcionProducto);
        txtPrecioReducido = findViewById(R.id.txtPrecioReducido);
        txtPrecioOriginal = findViewById(R.id.txtPrecioOriginal);
        txtVencimiento = findViewById(R.id.txtVencimiento);
        txtStock = findViewById(R.id.txtStock);
        txtUnidadVenta = findViewById(R.id.txtUnidadVenta);
        txtCategoria = findViewById(R.id.txtCategoria);
        txtComercio = findViewById(R.id.txtComercio);

        txtCantidad = findViewById(R.id.txtCantidad);
        btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito);

        productoId = getIntent().getIntExtra("productoId", -1);

        if (productoId == -1) {
            mostrarError("No se pudo identificar el producto");
            btnAgregarCarrito.setEnabled(false);
            return;
        }

        btnAgregarCarrito.setOnClickListener(v -> agregarAlCarrito());

        cargarProducto(productoId);
    }

    private void cargarProducto(int productoId) {

        ProductoApi api = ApiClient.getClient().create(ProductoApi.class);

        api.obtenerProductoPorId(productoId).enqueue(
                new Callback<ProductoResponse>() {

                    @Override
                    public void onResponse(
                            Call<ProductoResponse> call,
                            Response<ProductoResponse> response) {

                        if (isFinishing() || isDestroyed()) {
                            return;
                        }

                        if (response.isSuccessful()
                                && response.body() != null) {

                            mostrarProducto(response.body());

                        } else {
                            mostrarError(
                                    "No se pudo cargar el detalle del producto"
                            );
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ProductoResponse> call,
                            Throwable t) {

                        if (isFinishing() || isDestroyed()) {
                            return;
                        }

                        mostrarError(
                                "No se pudo conectar con el servidor"
                        );
                    }
                }
        );
    }

    private void agregarAlCarrito() {

        String cantidadTexto =
                txtCantidad.getText().toString().trim();

        if (cantidadTexto.isEmpty()) {
            Toast.makeText(
                    this,
                    "Ingresá una cantidad",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        int cantidad;

        try {
            cantidad = Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException e) {
            Toast.makeText(
                    this,
                    "Cantidad inválida",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (cantidad <= 0) {
            Toast.makeText(
                    this,
                    "La cantidad debe ser mayor a cero",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

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

        CarritoAgregarRequest request =
                new CarritoAgregarRequest(
                        idUsuario,
                        productoId,
                        cantidad
                );

        CarritoApi api =
                ApiClient.getClient().create(CarritoApi.class);

        btnAgregarCarrito.setEnabled(false);

        api.agregarProducto(request).enqueue(
                new Callback<Map<String, Object>>() {

                    @Override
                    public void onResponse(
                            Call<Map<String, Object>> call,
                            Response<Map<String, Object>> response) {

                        btnAgregarCarrito.setEnabled(true);

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    DetalleProductoActivity.this,
                                    "Producto agregado al carrito",
                                    Toast.LENGTH_LONG
                            ).show();

                        } else {

                            Toast.makeText(
                                    DetalleProductoActivity.this,
                                    "No se pudo agregar el producto al carrito",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<Map<String, Object>> call,
                            Throwable t) {

                        btnAgregarCarrito.setEnabled(true);

                        Toast.makeText(
                                DetalleProductoActivity.this,
                                "No se pudo conectar con el servidor",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }

    private void mostrarProducto(ProductoResponse producto) {

        txtNombreProducto.setText(
                producto.getNombre() != null
                        ? producto.getNombre()
                        : "Producto"
        );

        txtDescripcionProducto.setText(
                producto.getDescripcion() != null
                        ? producto.getDescripcion()
                        : "Sin descripción"
        );

        txtPrecioReducido.setText(
                "Precio Eco Umami: $" + producto.getPrecioReducido()
        );

        txtPrecioOriginal.setText(
                "Precio original: $" + producto.getPrecioOriginal()
        );

        txtVencimiento.setText(
                "Vencimiento: " + producto.getFechaVencimiento()
        );

        txtStock.setText(
                "Stock disponible: " + producto.getStock()
        );

        txtUnidadVenta.setText(
                "Unidad de venta: " + producto.getUnidadVenta()
        );

        txtCategoria.setText(
                "Categoría: " + producto.getCategoriaId()
        );

        txtComercio.setText(
                "Comercio: " + producto.getVendedorId()
        );
    }

    private void mostrarError(String mensaje) {
        txtNombreProducto.setText(mensaje);
        txtDescripcionProducto.setText("");
    }
}