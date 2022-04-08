package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InventoryActivity extends AppCompatActivity {

    private Button btnVentaClass;
    private Button btnCompraClass;
    private Button btnRegresarClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventario);

        btnVentaClass = findViewById(R.id.btnVentaInventory);
        btnCompraClass = findViewById(R.id.btnCompraInventory);
        btnRegresarClass = findViewById(R.id.btnRegresarInventory);

        btnVentaClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentaActivity();
            }
        });

        btnCompraClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCompraActivity();
            }
        });

        btnRegresarClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegresarActivity();
            }
        });
    }

    public void openVentaActivity(){
        Intent intent = new Intent(InventoryActivity.this, SaleActivity.class);
        startActivity(intent);
    }

    public void openCompraActivity(){
        Intent intent = new Intent(InventoryActivity.this, PurchaseActivity.class);
        startActivity(intent);
    }

    public void openRegresarActivity(){
        Intent intent = new Intent(InventoryActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}