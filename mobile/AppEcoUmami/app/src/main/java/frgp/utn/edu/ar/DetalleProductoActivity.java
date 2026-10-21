package frgp.utn.edu.ar;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        int productoId = getIntent().getIntExtra("productoId", -1);

        if (productoId == -1) {
            mostrarError("No se pudo identificar el producto");
            return;
        }

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