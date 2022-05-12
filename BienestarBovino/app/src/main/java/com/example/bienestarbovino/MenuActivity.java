package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;


public class MenuActivity extends AppCompatActivity {

    private Button btnSincronizar, btnAddBovino;
    ImageButton btnGanado, btnPesaje, btnChequeo, btnControlReproductivo;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth mAuth;


    //pruebas
    //private TextView nombre;
    //private TextView tamanno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        mAuth = FirebaseAuth.getInstance();
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSincronizar = findViewById(R.id.btnSincronizar);
        btnGanado = findViewById(R.id.imageButtonGanado);
        btnPesaje = findViewById(R.id.imageButtonPesaje);
        btnChequeo = findViewById(R.id.botonchequeo);
        btnAddBovino = findViewById(R.id.buttonAgregarBovino);
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

        btnPesaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPesaje();
            }
        });

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

        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });
    }
    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public void openPesaje(){
        Intent intent = new Intent(MenuActivity.this, WeightProductionActivity.class);
        startActivity(intent);
    }

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

    public void cerrarSesion(){
        mAuth.signOut();
        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}