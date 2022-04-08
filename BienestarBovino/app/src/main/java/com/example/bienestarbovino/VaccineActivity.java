package com.example.bienestarbovino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class VaccineActivity extends AppCompatActivity {

    private Button buttonVacunaRegresarClass;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacunacion);

        buttonVacunaRegresarClass = findViewById(R.id.btnVacunaRegresar);

        buttonVacunaRegresarClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVacunaRegresarActivity(view);
            }
        });
    }

    public void openVacunaRegresarActivity(View view){
        Intent intent = new Intent(VaccineActivity.this, VetMenuActivity.class);
        startActivity(intent);
    }

}
