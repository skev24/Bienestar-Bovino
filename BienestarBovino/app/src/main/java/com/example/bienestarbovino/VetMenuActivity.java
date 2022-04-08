package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class VetMenuActivity extends AppCompatActivity {

    private ImageButton buttonVacunaClass;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chequeo_menu);

        buttonVacunaClass = findViewById(R.id.btnVacuna);

        buttonVacunaClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVaccineActivity();
            }
        });
    }

    public void openVaccineActivity(){
        Intent intent = new Intent(VetMenuActivity.this, VaccineActivity.class);
        startActivity(intent);
    }

    public void returnMenuPrincipal(){
        Intent intent = new Intent(VetMenuActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
