package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SaleActivity extends AppCompatActivity {

    private Button buttonVentaRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.venta);

        buttonVentaRegresar = findViewById(R.id.btnVentaRegresar);

        buttonVentaRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentaRegresarActivity();
            }
        });
    }

    public void openVentaRegresarActivity(){
        Intent intent = new Intent(SaleActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}