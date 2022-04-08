package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VetMenuActivity extends AppCompatActivity {

    private Button buttonVacunaClass;

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
}
