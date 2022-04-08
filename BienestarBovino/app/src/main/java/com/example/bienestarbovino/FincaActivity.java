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


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import model.finca;

public class FincaActivity extends AppCompatActivity {

    private Button btnMenu;
    private EditText nameFinca, tamFinca;

    private FirebaseAuth mAuth;
    private finca newFinca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finca);

        nameFinca = findViewById(R.id.editTextTextFincaName);
        tamFinca = findViewById(R.id.editTextTextFincaSize);

        mAuth = FirebaseAuth.getInstance();

        newFinca = new finca();

        btnMenu = findViewById(R.id.buttonContinueFinca);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFinca(view);
            }
        });
    }

    public void createFinca(View view){

        Intent intent = new Intent(FincaActivity.this, MenuActivity.class);
        startActivity(intent);
    }


}