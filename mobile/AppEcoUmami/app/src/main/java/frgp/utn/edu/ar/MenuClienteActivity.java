package frgp.utn.edu.ar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuClienteActivity extends AppCompatActivity {

    Button btnCatalogo, btnCarrito, btnPedidos, btnPerfil, btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menucliente);

        btnCatalogo = findViewById(R.id.btnCatalogo);
        btnCarrito = findViewById(R.id.btnCarrito);
        btnPedidos = findViewById(R.id.btnPedidos);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // CATÁLOGO
        btnCatalogo.setOnClickListener(v -> {
            Intent intent = new Intent(MenuClienteActivity.this, CatalogoClienteActivity.class);
            startActivity(intent);
        });

        // CARRITO
        btnCarrito.setOnClickListener(v ->
                Toast.makeText(MenuClienteActivity.this,
                        "Proximamente",
                        Toast.LENGTH_SHORT).show()
        );

        // PEDIDOS
        btnPedidos.setOnClickListener(v ->
                Toast.makeText(MenuClienteActivity.this,
                        "Proximamente",
                        Toast.LENGTH_SHORT).show()
        );

        // PERFIL
        btnPerfil.setOnClickListener(v ->
                Toast.makeText(MenuClienteActivity.this,
                        "Proximamente",
                        Toast.LENGTH_SHORT).show()
        );

        // CERRAR SESIÓN
        btnCerrarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(MenuClienteActivity.this, Principal.class);
            startActivity(intent);
            finish();
        });
    }
}