package frgp.utn.edu.ar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuNegocioActivity extends AppCompatActivity {

    Button btnMisProductos, btnNuevoProducto, btnPedidosRecibidos, btnPerfil, btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menunegocio);

        btnMisProductos = findViewById(R.id.btnMisProductos);
        btnNuevoProducto = findViewById(R.id.btnNuevoProducto);
        btnPedidosRecibidos = findViewById(R.id.btnPedidosRecibidos);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // MIS PRODUCTOS
        btnMisProductos.setOnClickListener(v ->
                Toast.makeText(MenuNegocioActivity.this,
                        "Proximamente",
                        Toast.LENGTH_SHORT).show()
        );

        // NUEVO PRODUCTO
        btnNuevoProducto.setOnClickListener(v -> {
            Intent intent = new Intent(MenuNegocioActivity.this, AltaProductoActivity.class);
            startActivity(intent);
        });

        // PEDIDOS RECIBIDOS
        btnPedidosRecibidos.setOnClickListener(v ->
                Toast.makeText(MenuNegocioActivity.this,
                        "Proximamente",
                        Toast.LENGTH_SHORT).show()
        );

        // PERFIL
        btnPerfil.setOnClickListener(v ->
                Toast.makeText(MenuNegocioActivity.this,
                        "Proximamente",
                        Toast.LENGTH_SHORT).show()
        );

        // CERRAR SESIÓN
        btnCerrarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(MenuNegocioActivity.this, Principal.class);
            startActivity(intent);
            finish();
        });
    }
}