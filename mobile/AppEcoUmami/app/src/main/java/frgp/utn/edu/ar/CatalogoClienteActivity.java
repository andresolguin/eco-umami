package frgp.utn.edu.ar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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

        recyclerProductos.setLayoutManager(new LinearLayoutManager(this));

        productoAdapter = new ProductoAdapter(productos);
        recyclerProductos.setAdapter(productoAdapter);

        actualizarEstadoCatalogo();
    }

    private void actualizarEstadoCatalogo() {
        if (productos.isEmpty()) {
            txtEstadoCatalogo.setVisibility(View.VISIBLE);
            recyclerProductos.setVisibility(View.GONE);
        } else {
            txtEstadoCatalogo.setVisibility(View.GONE);
            recyclerProductos.setVisibility(View.VISIBLE);
        }
    }
}