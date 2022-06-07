package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import control.Funciones;

public class InformeProduccionActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, Funciones {

    private Button btnRegresar, btnGuardar;
    private Spinner vacasGenero, vacasDieta;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String generoSpin = "";
    private String dietaSpin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_produccion);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        vacasDieta = findViewById(R.id.spinnerDietaInfoProduccion);
        vacasGenero = findViewById(R.id.spinnerSexoInfoProduccion);

        btnRegresar = findViewById(R.id.btnInfoBovinoRegresar);
        btnGuardar = findViewById(R.id.buttonInfoGenerar);

        cargarSpinners();
    }

    public void cargarSpinners(){
        ArrayAdapter<CharSequence> adapterVacas = ArrayAdapter.createFromResource(this,
                R.array.genero, android.R.layout.simple_spinner_item);
        adapterVacas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vacasGenero.setAdapter(adapterVacas);
        vacasGenero.setOnItemSelectedListener(this);

        //dieta
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        generoSpin = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), tipoSpin, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void goBack(){
        Intent intent = new Intent(InformeProduccionActivity.this, ControlReproductivoActivity.class);
        startActivity(intent);
    }
}