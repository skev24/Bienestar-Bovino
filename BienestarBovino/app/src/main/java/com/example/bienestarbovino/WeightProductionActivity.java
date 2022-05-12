package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import control.Funciones;
import model.produccionPeso;

public class WeightProductionActivity extends AppCompatActivity implements Funciones {

    private EditText textDate;
    private Button buttonPesoRegresarClass;
    private Spinner setBovino;
    private EditText textPesoBovino, textPesoNotas;
    private Button buttonAddPeso;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produccion_peso);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        textDate = findViewById(R.id.editFechaPeso);
        buttonPesoRegresarClass = findViewById(R.id.buttonPesoRegresar);
        setBovino = findViewById(R.id.spinnerBovinoPeso);
        textPesoBovino = findViewById(R.id.editPesoVaca);
        textPesoNotas = findViewById(R.id.editTextNotasPeso);
        buttonAddPeso = findViewById(R.id.buttonPesoRegister);

        buttonPesoRegresarClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar(view);
            }
        });

        buttonAddPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPeso(view);
            }
        });
    }

    public void goBack(){
        Intent intent = new Intent(WeightProductionActivity.this, MenuActivity.class);
        startActivity(intent);
    }


    public void openCalendar(View view){
        Calendar calendar = Calendar.getInstance();
        int yearD = calendar.get(Calendar.YEAR);
        int monthD = calendar.get(Calendar.MONTH);
        int dayD = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(WeightProductionActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                textDate.setText(date);
            }

        }, yearD, monthD, dayD);
        dpd.show();
    }

    public void addPeso(View view){
        String bovino = "toro 1";//selBovino spinner
        String peso = textPesoBovino.getText().toString();
        String notas = textPesoNotas.getText().toString();
        String fecha = textDate.getText().toString();
        String dieta = "Forrajes";

        if (TextUtils.isEmpty(bovino) && TextUtils.isEmpty(peso) && TextUtils.isEmpty(fecha)) {
            Toast.makeText(WeightProductionActivity.this, "Ingrese todos los datos.", Toast.LENGTH_SHORT).show();
        } else {
            addDatatoFirebase(bovino, peso, fecha, dieta, notas, view);
        }
    }

    //addDataToFirebase agrega a la base de datos el registro de vacuna
    private void addDatatoFirebase(String p_bovino, String p_peso, String p_fecha, String p_dieta, String p_notas, View view) {

        CollectionReference dbVacuna = db.collection("produccionPeso");
        produccionPeso nuevoPesaje = new produccionPeso(p_bovino, p_peso, p_fecha, p_dieta, p_notas);

        dbVacuna.add(nuevoPesaje).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(WeightProductionActivity.this, "Registro de pesaje agregado.", Toast.LENGTH_SHORT).show();
                goBack();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WeightProductionActivity.this, "Error al agregar registro de pesaje. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

}