package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuVetActivity extends AppCompatActivity {

    private ImageButton buttonVacunaClass;
    private Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chequeo_menu);

        buttonVacunaClass = findViewById(R.id.btnVacuna);
        btnRegresar = findViewById(R.id.buttonBackVet);

        buttonVacunaClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVaccineActivity();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnMenuPrincipal();
            }
        });
    }

    public void openVaccineActivity(){
        Intent intent = new Intent(MenuVetActivity.this, VaccineActivity.class);
        startActivity(intent);
    }

    public void returnMenuPrincipal(){
        Intent intent = new Intent(MenuVetActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}