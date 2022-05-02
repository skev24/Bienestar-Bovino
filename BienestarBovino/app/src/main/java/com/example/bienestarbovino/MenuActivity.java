package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MenuActivity extends AppCompatActivity {

    private Button btnSincronizar, btnAddBovino;
    ImageButton btnGanado, btnPesaje, btnChequeo, btnControlReproductivo;

    //pruebas
    //private TextView nombre;
    //private TextView tamanno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprincipal);

        btnSincronizar = findViewById(R.id.btnRegresarTratamiento);
        btnGanado = findViewById(R.id.imageButtonGanado);
        //btnPesaje = findViewById(R.id.imageButtonPesaje);
        btnChequeo = findViewById(R.id.botonchequeo);
        btnAddBovino = findViewById(R.id.buttonGuardarTratamiento);
        btnControlReproductivo = findViewById(R.id.imageButtonControlReproductivo);

// pruebas
        //db = FirebaseDatabase.getInstance().getReference();

        /*btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(view);
            }
        });*/

        btnGanado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGanado();
            }
        });

        /*btnPesaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPesaje();
            }
        });*/

        btnChequeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChequeo();
            }
        });

        btnAddBovino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddBovino();
            }
        });

        btnControlReproductivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openControlReproductivo();
            }
        });
    }

    /*public void getData(View view){

        db.child("Fincas").child("a9f87f94-fbe9-40a0-83f4-ac20bf9eaa1d").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String id = snapshot.child("name").getValue().toString();
                    Toast.makeText(MenuActivity.this, id, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    public void openGanado(){
        Intent intent = new Intent(MenuActivity.this, InventoryActivity.class);
        startActivity(intent);
    }

    /*public void openPesaje(){
        //Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
        //startActivity(intent);
    }*/

    public void openChequeo(){
        Intent intent = new Intent(MenuActivity.this, MenuVetActivity.class);
        startActivity(intent);
    }

    public void openAddBovino(){
        Intent intent = new Intent(MenuActivity.this, NewBovinoActivity.class);
        startActivity(intent);
    }

    public void openControlReproductivo(){
        Intent intent = new Intent(MenuActivity.this, ControlReproductivoActivity.class);
        startActivity(intent);
    }
}