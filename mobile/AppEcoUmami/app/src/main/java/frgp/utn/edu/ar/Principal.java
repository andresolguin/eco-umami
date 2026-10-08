package frgp.utn.edu.ar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Principal extends ComponentActivity {

    private EditText txtUsuario;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView linkRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        txtUsuario = findViewById(R.id.txtUsuario);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        linkRegistro = findViewById(R.id.linkRegistro);

        btnLogin.setOnClickListener(v -> iniciarSesion());

        linkRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Principal.this,
                    RegistroActivity.class
            );
            startActivity(intent);
        });
    }

    private void iniciarSesion() {

        String mail = txtUsuario.getText().toString().trim();
        String pass = txtPassword.getText().toString().trim();

        if (mail.isEmpty() || pass.isEmpty()) {
            Toast.makeText(
                    this,
                    "Completá todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        LoginRequest request = new LoginRequest(mail, pass);

        AuthApi api = ApiClient.getClient().create(AuthApi.class);

        api.login(request).enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(
                    Call<Usuario> call,
                    Response<Usuario> response
            ) {

                if (response.isSuccessful() && response.body() != null) {

                    Usuario usuario = response.body();

                    if (usuario.getTipoUsuario() == null) {
                        Toast.makeText(
                                Principal.this,
                                "No se pudo determinar el tipo de usuario",
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    Integer tipoUsuario =
                            usuario.getTipoUsuario().getId();

                    if (tipoUsuario == 2) {

                        Intent intent = new Intent(
                                Principal.this,
                                MenuClienteActivity.class
                        );

                        startActivity(intent);
                        finish();

                    } else if (tipoUsuario == 3) {

                        Intent intent = new Intent(
                                Principal.this,
                                MenuNegocioActivity.class
                        );

                        startActivity(intent);
                        finish();

                    } else {

                        Toast.makeText(
                                Principal.this,
                                "Este tipo de usuario no puede ingresar desde la aplicación",
                                Toast.LENGTH_LONG
                        ).show();
                    }

                } else if (response.code() == 401) {

                    Toast.makeText(
                            Principal.this,
                            "Correo o contraseña incorrectos",
                            Toast.LENGTH_LONG
                    ).show();

                } else {

                    Toast.makeText(
                            Principal.this,
                            "Error al iniciar sesión. Código: "
                                    + response.code(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<Usuario> call,
                    Throwable t
            ) {

                Toast.makeText(
                        Principal.this,
                        "Error de conexión con el servidor",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}