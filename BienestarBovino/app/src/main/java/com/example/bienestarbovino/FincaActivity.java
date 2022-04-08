package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FincaActivity extends AppCompatActivity {

    private Button btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finca);

        btnMenu = findViewById(R.id.buttonContinueFinca);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenu(view);
            }
        });
    }

    public void openMenu(View view){
        Intent intent = new Intent(FincaActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}