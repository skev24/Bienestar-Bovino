package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import control.Funciones;

public class GestacionActivity extends AppCompatActivity implements Funciones {

    private Button btnRegresar, btnGuardar;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestacion);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btnRegresar = findViewById(R.id.btnRegresarGestacion);
        btnGuardar = findViewById(R.id.buttonGuardarGestacion);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goBack(); }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { guardarEstado(); }
        });
    }

    @Override
    public void goBack() {
        Intent intent = new Intent(GestacionActivity.this, ControlReproductivoActivity.class);
        startActivity(intent);
    }

    private void guardarEstado(){
//        String id = bovinosHash.get(bovinoActual);
//        db.collection("estadoReproductivo").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
//                    String idBovino = qs.getString("idBovino");
//                    if(idBovino.equals(id)){
//                        String destete = terneraDestete.getText().toString();
//                        String novillo = terneraNovillo.getText().toString();
//                        String adulta = terneraAdulta.getText().toString();
//
//                        db.collection("estadoReproductivo").document(qs.getId()).update("destete", "prueba2");
//                        db.collection("estadoReproductivo").document(qs.getId()).update("novilla", novillo);
//                        db.collection("estadoReproductivo").document(qs.getId()).update("adulta", adulta);
//                        Toast.makeText(EstadoReproductivoActivity.this, "Estado actualizado.", Toast.LENGTH_SHORT).show();
//                        btnGuardar.setEnabled(false);
//                        break;
//                    }
//                }
//            }
//        });
    }
}