package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class InventoryActivity extends AppCompatActivity{

    private Button btnVentaClass;
    private Button btnCompraClass;
    private Button btnRegresarClass;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventario);

        btnVentaClass = findViewById(R.id.btnVenta);
        btnCompraClass = findViewById(R.id.btnCompra);
        btnRegresarClass = findViewById(R.id.btnRegresar);

        btnVentaClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentaActivity(view);
            }
        });

        btnCompraClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCompraActivity(view);
            }
        });

        btnRegresarClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegresarActivity(view);
            }
        });
    }

    public void openVentaActivity(View view){
        Intent intent = new Intent(InventoryActivity.this, SaleActivity.class);
        startActivity(intent);
    }

    public void openCompraActivity(View view){
        Intent intent = new Intent(InventoryActivity.this, PurchaseActivity.class);
        startActivity(intent);
    }

    public void openRegresarActivity(View view){
        Intent intent = new Intent(InventoryActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
