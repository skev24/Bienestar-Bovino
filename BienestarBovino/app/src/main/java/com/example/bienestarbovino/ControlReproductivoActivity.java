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
    ImageButton btnEstadoReproductivo, btnGestacion, btnPartos, btnAbortos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_reproductivo);

        btnRegresar = findViewById(R.id.btnControlReproductivoRegresar);
        btnEstadoReproductivo = findViewById(R.id.imageButtonEstadoReproductivo);
        btnGestacion = findViewById(R.id.imageButtonGestacion);
        btnPartos = findViewById(R.id.imageButtonPartos);
        btnAbortos = findViewById(R.id.imageButtonAbortos);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        btnEstadoReproductivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openInfoEstadoReproductivo(); }
        });

        btnGestacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openSetGestacion(); }
        });

        btnPartos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openSetParto(); }
        });

        btnAbortos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openSetAborto(); }
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

    public void openSetGestacion(){
        Intent intent = new Intent(ControlReproductivoActivity.this, GestacionActivity.class);
        startActivity(intent);
    }

    public void openSetParto(){
        Intent intent = new Intent(ControlReproductivoActivity.this, PartoActivity.class);
        startActivity(intent);
    }

    public void openSetAborto(){
        Intent intent = new Intent(ControlReproductivoActivity.this, AbortoActivity.class);
        startActivity(intent);
    }

}