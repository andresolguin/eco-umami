package frgp.utn.edu.ar;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import android.content.Intent;
import android.widget.TextView;
import android.view.View;

public class RegistroActivity extends ComponentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        TextView linkLogin = findViewById(R.id.linkIniciarSesio);

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegistroActivity.this, Principal.class);
                startActivity(intent);

            }
        });
    }
}
