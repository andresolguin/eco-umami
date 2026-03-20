package frgp.utn.edu.ar;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import android.content.Intent;
import android.widget.TextView;
import android.view.View;

public class Principal extends ComponentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        TextView linkRegistro = findViewById(R.id.linkRegistro);

        linkRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Principal.this, RegistroActivity.class);
                startActivity(intent);

            }
        });
    }
}