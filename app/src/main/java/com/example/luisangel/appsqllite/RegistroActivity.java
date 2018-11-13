package com.example.luisangel.appsqllite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.luisangel.appsqllite.modelos.Persona;
import com.example.luisangel.appsqllite.sqlite.MetodoSQL;

public class RegistroActivity extends AppCompatActivity {
    private EditText etNombre, etApellido;
    private Button btnAgregar;
    private Spinner spGenero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellido = (EditText) findViewById(R.id.etApellido);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        spGenero = (Spinner) findViewById(R.id.spGenero);

        btnAgregar.setTag(0);

        if (getIntent().hasExtra("id")) {
            int id = getIntent().getIntExtra("id", 0);
            btnAgregar.setTag(id);

            String nombre = getIntent().getStringExtra("nombre");
            String apellido = getIntent().getStringExtra("apellido");
            String genero = getIntent().getStringExtra("genero");

            etNombre.setText(nombre);
            etApellido.setText(apellido);
            String[] generos = getResources().getStringArray(R.array.genero);
            for (int i = 0; i < generos.length; i++) {
                if (generos[i].equals(genero)) {
                    spGenero.setSelection(i);
                    break;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = etNombre.getText().toString();
                String apellido = etApellido.getText().toString();
                String genero = spGenero.getSelectedItem().toString();

                Persona persona = new Persona(0, nombre, apellido,  genero);

                MetodoSQL metodoSQL = new MetodoSQL(getApplicationContext());

                int idPersona = (int) btnAgregar.getTag();
                if (idPersona == 0) {
                    metodoSQL.agregarPersona(persona);
                    Toast.makeText(RegistroActivity.this,
                            "Se registro correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    persona.setId(idPersona);
                    metodoSQL.actualizarPersona(persona);
                    Toast.makeText(RegistroActivity.this,
                            "Se actualizo correctamente", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
