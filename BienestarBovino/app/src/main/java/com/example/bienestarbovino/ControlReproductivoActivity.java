package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import control.Funciones;

public class ControlReproductivoActivity extends AppCompatActivity implements Funciones {

    Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_reproductivo);

        btnRegresar = findViewById(R.id.btnControlReproductivoRegresar);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });


    }

    public void goBack(){
        Intent intent = new Intent(ControlReproductivoActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}