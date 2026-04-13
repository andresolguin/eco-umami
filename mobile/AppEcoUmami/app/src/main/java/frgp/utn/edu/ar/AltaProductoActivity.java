package frgp.utn.edu.ar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.math.BigDecimal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AltaProductoActivity extends AppCompatActivity {

    EditText etCodigo, etNombre, etDescripcion, etPrecioOriginal,
            etPrecioReducido, etStock, etUnidadVenta, etFechaVencimiento;
    Button btnGuardarProducto, btnVolverMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altaproducto);

        etCodigo = findViewById(R.id.etCodigo);
        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        etPrecioOriginal = findViewById(R.id.etPrecioOriginal);
        etPrecioReducido = findViewById(R.id.etPrecioReducido);
        etStock = findViewById(R.id.etStock);
        etUnidadVenta = findViewById(R.id.etUnidadVenta);
        etFechaVencimiento = findViewById(R.id.etFechaVencimiento);

        btnGuardarProducto = findViewById(R.id.btnGuardarProducto);
        btnVolverMenu = findViewById(R.id.btnVolverMenu);

        etFechaVencimiento.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            new DatePickerDialog(
                    AltaProductoActivity.this,
                    (view, y, m, d) -> {
                        String fecha = y + "-" +
                                String.format("%02d", m + 1) + "-" +
                                String.format("%02d", d);
                        etFechaVencimiento.setText(fecha);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        btnVolverMenu.setOnClickListener(v -> {
            Intent intent = new Intent(AltaProductoActivity.this, MenuNegocioActivity.class);
            startActivity(intent);
            finish();
        });

        btnGuardarProducto.setOnClickListener(v -> {

            try {

                ProductoRequest producto = new ProductoRequest();

                producto.setCodigo(etCodigo.getText().toString());
                producto.setNombre(etNombre.getText().toString());
                producto.setDescripcion(etDescripcion.getText().toString());
                producto.setPrecioOriginal(new BigDecimal(etPrecioOriginal.getText().toString()));
                producto.setPrecioReducido(new BigDecimal(etPrecioReducido.getText().toString()));
                producto.setFechaVencimiento(etFechaVencimiento.getText().toString());
                producto.setStock(Integer.parseInt(etStock.getText().toString()));
                producto.setUnidadVenta(etUnidadVenta.getText().toString());

                producto.setCategoriaId(1);
                producto.setVendedorId(1);

                ProductoApi api = ApiClient.getClient().create(ProductoApi.class);

                Call<Void> call = api.crearProducto(producto);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AltaProductoActivity.this,
                                    "Producto creado correctamente",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AltaProductoActivity.this,
                                    "Error: " + response.code(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AltaProductoActivity.this,
                                "Error de conexión: " + t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
