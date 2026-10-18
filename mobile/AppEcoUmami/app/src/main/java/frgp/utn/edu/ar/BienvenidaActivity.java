package frgp.utn.edu.ar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.ComponentActivity;

public class BienvenidaActivity extends ComponentActivity {

    private Button btnIniciarSesion;
    private Button btnCrearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        btnIniciarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(
                    BienvenidaActivity.this,
                    Principal.class
            );
            startActivity(intent);
        });

        btnCrearCuenta.setOnClickListener(v -> {
            Intent intent = new Intent(
                    BienvenidaActivity.this,
                    RegistroActivity.class
            );
            startActivity(intent);
        });
    }
}