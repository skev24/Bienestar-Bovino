package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import model.Register;
import model.finca;

public class FincaActivity extends AppCompatActivity {

    private Button btnMenu;
    private EditText nameFinca, tamFinca;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finca);

        nameFinca = findViewById(R.id.editTextTextFincaName);
        tamFinca = findViewById(R.id.editTextTextFincaSize);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        btnMenu = findViewById(R.id.buttonContinueFinca);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFinca(view);
            }
        });
    }

    public void createFinca(View view){
        String name = nameFinca.getText().toString();
        String tam = tamFinca.getText().toString();
        String userID = mAuth.getUid();

        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(tam) && TextUtils.isEmpty(userID)) {

            Toast.makeText(FincaActivity.this, "Ingrese los datos.", Toast.LENGTH_SHORT).show();
        } else {

            addDatatoFirebase(name, tam, userID);
        }
        Intent intent = new Intent(FincaActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    private void addDatatoFirebase(String p_name, String p_tam, String p_user) {

        CollectionReference dbFincas = db.collection("finca");

        finca nuevaFinca = new finca(p_name, p_tam, p_user);

        dbFincas.add(nuevaFinca).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(FincaActivity.this, "finca creada.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FincaActivity.this, "Error al crear finca. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                // inside the method of on Data change we are setting
//                // our object class to our database reference.
//                // data base reference will sends data to firebase.
//                databaseReference.child(uuid).setValue(newFinca);
//                // after adding this data we are showing toast message.
//                Toast.makeText(FincaActivity.this, "Finca creada", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // if the data is not added or it is cancelled then
//                // we are displaying a failure toast message.
//                Toast.makeText(FincaActivity.this, "No se pudo crear la finca, " + error, Toast.LENGTH_SHORT).show();
//            }
//        });
    }


}