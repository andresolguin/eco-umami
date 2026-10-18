package frgp.utn.edu.ar;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        String nombre = getIntent().getStringExtra("nombre");
        String descripcion = getIntent().getStringExtra("descripcion");
        String precioReducido = getIntent().getStringExtra("precioReducido");
        String precioOriginal = getIntent().getStringExtra("precioOriginal");
        String fechaVencimiento = getIntent().getStringExtra("fechaVencimiento");
        String stock = getIntent().getStringExtra("stock");
        String unidadVenta = getIntent().getStringExtra("unidadVenta");
        String categoria = getIntent().getStringExtra("categoria");
        String comercio = getIntent().getStringExtra("comercio");

        txtNombreProducto.setText(nombre != null ? nombre : "Producto");
        txtDescripcionProducto.setText(descripcion != null ? descripcion : "Sin descripción");
        txtPrecioReducido.setText("Precio Eco Umami: $" + (precioReducido != null ? precioReducido : "-"));
        txtPrecioOriginal.setText("Precio original: $" + (precioOriginal != null ? precioOriginal : "-"));
        txtVencimiento.setText("Vencimiento: " + (fechaVencimiento != null ? fechaVencimiento : "-"));
        txtStock.setText("Stock disponible: " + (stock != null ? stock : "-"));
        txtUnidadVenta.setText("Unidad de venta: " + (unidadVenta != null ? unidadVenta : "-"));
        txtCategoria.setText("Categoría: " + (categoria != null ? categoria : "-"));
        txtComercio.setText("Comercio: " + (comercio != null ? comercio : "-"));
    }
}