package frgp.utn.edu.ar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;

import androidx.activity.ComponentActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends ComponentActivity {

    private EditText txtUsuarioRegistro;
    private EditText txtPasswordRegistro;

    private Spinner spinnerTipoUsuario;
    private Spinner spinnerTipoPersona;

    private Button btnRegistro;
    private TextView linkIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtUsuarioRegistro = findViewById(R.id.txtUsuarioRegistro);
        txtPasswordRegistro = findViewById(R.id.txtPasswordRegistro);

        spinnerTipoUsuario = findViewById(R.id.spinnerTipoUsuario);
        spinnerTipoPersona = findViewById(R.id.spinnerTipoPersona);

        btnRegistro = findViewById(R.id.btnRegistro);
        linkIniciarSesion = findViewById(R.id.linkIniciarSesio);

        configurarSpinnerTipoUsuario();
        configurarSpinnerTipoPersona();

        btnRegistro.setOnClickListener(v -> registrarUsuario());

        linkIniciarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroActivity.this, Principal.class);
            startActivity(intent);
            finish();
        });
    }

    private void configurarSpinnerTipoUsuario() {

        String[] tiposUsuario = {
                "Cliente",
                "Comercio"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tiposUsuario
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerTipoUsuario.setAdapter(adapter);
    }

    private void configurarSpinnerTipoPersona() {

        String[] tiposPersona = {
                "Física",
                "Jurídica"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tiposPersona
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerTipoPersona.setAdapter(adapter);
    }

    private void registrarUsuario() {

        String mail = txtUsuarioRegistro.getText().toString().trim();
        String pass = txtPasswordRegistro.getText().toString().trim();

        if (mail.isEmpty() || pass.isEmpty()) {
            Toast.makeText(
                    this,
                    "Completá todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(
                    this,
                    "Ingresá un correo electrónico válido",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }
        if (pass.length() < 8) {
            Toast.makeText(
                    this,
                    "La contraseña debe tener al menos 8 caracteres",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (!pass.matches(".*[A-Z].*")) {
            Toast.makeText(
                    this,
                    "La contraseña debe tener al menos una mayúscula",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (!pass.matches(".*[a-z].*")) {
            Toast.makeText(
                    this,
                    "La contraseña debe tener al menos una minúscula",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (!pass.matches(".*[0-9].*")) {
            Toast.makeText(
                    this,
                    "La contraseña debe tener al menos un número",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        // Cliente = 2
        // Comercio = 3
        int idTipoUsuario;

        if (spinnerTipoUsuario.getSelectedItemPosition() == 0) {
            idTipoUsuario = 2;
        } else {
            idTipoUsuario = 3;
        }

        // Física = 1
        // Jurídica = 2
        int idTipoPersona = spinnerTipoPersona.getSelectedItemPosition() + 1;

        // Un cliente solamente puede ser persona física
        if (idTipoUsuario == 2) {
            idTipoPersona = 1;
        }

        RegisterRequest request = new RegisterRequest(
                mail,
                pass,
                idTipoUsuario,
                idTipoPersona
        );

        AuthApi api = ApiClient.getClient().create(AuthApi.class);

        api.register(request).enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(
                    Call<Usuario> call,
                    Response<Usuario> response
            ) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            RegistroActivity.this,
                            "Usuario registrado correctamente",
                            Toast.LENGTH_LONG
                    ).show();

                    Intent intent = new Intent(
                            RegistroActivity.this,
                            Principal.class
                    );

                    startActivity(intent);
                    finish();

                } else {
                    if (response.code() == 409) {
                        Toast.makeText(
                                RegistroActivity.this,
                                "El correo electrónico ya está registrado",
                                Toast.LENGTH_LONG
                        ).show();
                    } else {
                        Toast.makeText(
                                RegistroActivity.this,
                                "Error al registrar. Código: " + response.code(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
            }

            @Override
            public void onFailure(
                    Call<Usuario> call,
                    Throwable t
            ) {

                Toast.makeText(
                        RegistroActivity.this,
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}
