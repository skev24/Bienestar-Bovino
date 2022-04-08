package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VaccineActivity extends AppCompatActivity {

    private Button buttonVacunaRegresarClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacunacion);

        buttonVacunaRegresarClass = findViewById(R.id.btnRegresarVacunacion);

        buttonVacunaRegresarClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVacunaRegresarActivity(view);
            }
        });
    }

    public void openVacunaRegresarActivity(View view){
        Intent intent = new Intent(VaccineActivity.this, MenuVetActivity.class);
        startActivity(intent);
    }
}