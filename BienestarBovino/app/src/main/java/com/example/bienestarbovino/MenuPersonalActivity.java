package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import control.Funciones;
import model.Tarea;

public class MenuPersonalActivity extends AppCompatActivity {

    private ImageButton btnPersonal, btnTarea;
    private Button btnReturn;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_personal);

        btnPersonal = findViewById(R.id.btnPersonal2);
        btnTarea = findViewById(R.id.btnTareas2);
        btnReturn = findViewById(R.id.btnRegPersonal);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        btnTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTarea(view);
            }
        });

        btnPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPersonal(view);
            }
        });

    }

    public void openTarea(View view){
        Intent intent = new Intent(MenuPersonalActivity.this, TaskActivity.class);
        startActivity(intent);
    }

    public void openPersonal(View view){
        Intent intent = new Intent(MenuPersonalActivity.this, PersonalActivity.class);
        startActivity(intent);
    }

    public void goBack(){
        Intent intent = new Intent(MenuPersonalActivity.this, TaskActivity.class);
        startActivity(intent);
    }
}
