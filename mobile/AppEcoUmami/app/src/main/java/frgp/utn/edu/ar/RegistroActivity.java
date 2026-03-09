package frgp.utn.edu.ar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    EditText usuario;
    EditText password;
    Button botonRegistro;
    TextView linkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        usuario = findViewById(R.id.txtUsuarioRegistro);
        password = findViewById(R.id.txtPasswordRegistro);
        botonRegistro = findViewById(R.id.btnRegistro);
        linkLogin = findViewById(R.id.linkIniciarSesio);

        botonRegistro.setOnClickListener(v -> {
            String user = usuario.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if(user.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Principal.class);
                startActivity(intent);
            }
        });

        linkLogin.setOnClickListener(v -> {
            finish(); // vuelve al login
        });
    }
}