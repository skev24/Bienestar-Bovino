package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import control.Funciones;

public class MenuVetActivity extends AppCompatActivity implements Funciones {

    private ImageButton buttonVacunaClass, buttonTratamientoClass;
    private Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_chequeo);

        buttonTratamientoClass = findViewById(R.id.btnTratamiento);
        buttonVacunaClass = findViewById(R.id.btnVacuna);
        btnRegresar = findViewById(R.id.buttonBackVet);

        buttonVacunaClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVaccineActivity();
            }
        });

        buttonTratamientoClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTreatmentActivity();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    public void openVaccineActivity(){
        Intent intent = new Intent(MenuVetActivity.this, VaccineActivity.class);
        startActivity(intent);
    }

    public void goBack(){
        Intent intent = new Intent(MenuVetActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void openTreatmentActivity(){
        Intent intent = new Intent(MenuVetActivity.this, TreatmentActivity.class);
        startActivity(intent);
    }
}