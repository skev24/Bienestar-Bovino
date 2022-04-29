package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import control.Funciones;

public class EstadoReproductivoActivity extends AppCompatActivity implements Funciones {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_estado_reproductivo);
    }

    @Override
    public void goBack() {
        Intent intent = new Intent(EstadoReproductivoActivity.this, ControlReproductivoActivity.class);
        startActivity(intent);
    }
}