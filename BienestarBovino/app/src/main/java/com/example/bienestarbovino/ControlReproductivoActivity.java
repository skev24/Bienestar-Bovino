package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import control.Funciones;

public class ControlReproductivoActivity extends AppCompatActivity implements Funciones {

    Button btnRegresar;
    ImageButton btnEstadoReproductivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_reproductivo);

        btnRegresar = findViewById(R.id.btnControlReproductivoRegresar);
        btnEstadoReproductivo = findViewById(R.id.imageButtonEstadoReproductivo);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        btnEstadoReproductivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInfoEstadoReproductivo();
            }
        });

    }

    public void goBack(){
        Intent intent = new Intent(ControlReproductivoActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void openInfoEstadoReproductivo(){
        Intent intent = new Intent(ControlReproductivoActivity.this, EstadoReproductivoActivity.class);
        startActivity(intent);
    }

}