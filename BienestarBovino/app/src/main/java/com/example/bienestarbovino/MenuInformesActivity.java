package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import control.Funciones;



public class MenuInformesActivity extends AppCompatActivity implements Funciones {

    private ImageButton btnInfoProduccion, btnInfoVentas;
    private Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.informes_estadisticas);

        btnInfoProduccion = findViewById(R.id.btnInfoProduccion);
        btnInfoVentas = findViewById(R.id.btnInfoVentas);
        btnRegresar = findViewById(R.id.btnRegInformes);

        btnInfoProduccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoProdu();
            }
        });

        btnInfoVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoVentas();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    public void infoProdu(){
        Intent intent = new Intent(MenuInformesActivity.this, InformeProduccionActivity.class);
        startActivity(intent);
    }
    public void infoVentas(){
        Intent intent = new Intent(MenuInformesActivity.this, InformeVentasActivity.class);
        startActivity(intent);
    }

    public void goBack(){
        Intent intent = new Intent(MenuInformesActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
