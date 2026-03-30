package frgp.utn.edu.ar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AltaProductoActivity extends AppCompatActivity {

    EditText etFechaVencimiento;
    Button btnVolverMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altaproducto);

        etFechaVencimiento = findViewById(R.id.etFechaVencimiento);
        btnVolverMenu = findViewById(R.id.btnVolverMenu);

        etFechaVencimiento.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(
                    AltaProductoActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {

                        String fecha = selectedYear + "-" +
                                String.format("%02d", selectedMonth + 1) + "-" +
                                String.format("%02d", selectedDay);

                        etFechaVencimiento.setText(fecha);

                    }, year, month, day
            );

            datePicker.show();
        });

        btnVolverMenu.setOnClickListener(v -> {

            Intent intent = new Intent(AltaProductoActivity.this, MenuNegocioActivity.class);
            startActivity(intent);
            finish();

        });
    }
}
