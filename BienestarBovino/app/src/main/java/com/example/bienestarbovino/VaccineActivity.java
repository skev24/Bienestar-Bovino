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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import model.bovino;
import model.finca;
import model.vacuna;

public class VaccineActivity extends AppCompatActivity {

    private TextView textDate;
    private Button buttonVacunaRegresarClass;
    private Spinner setBovino;
    private EditText textBovinoEnfermedad, textBovinoNotas;
    private Button buttonAddVacuna;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacunacion);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        textDate = findViewById(R.id.textViewDateVacunacion);
        buttonVacunaRegresarClass = findViewById(R.id.btnRegresarVacunacion);
        setBovino = findViewById(R.id.spinnerBovinoVacunacion);
        textBovinoEnfermedad = findViewById(R.id.editTextEnfermedad);
        textBovinoNotas = findViewById(R.id.editTextNotasVacunacion);
        buttonAddVacuna = findViewById(R.id.buttonGuardarVacunacion);
        buttonVacunaRegresarClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVacunaRegresarActivity(view);
            }
        });
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar(view);
            }
        });

        buttonAddVacuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVacunacion(view);
            }
        });
    }

    public void openVacunaRegresarActivity(View view){
        Intent intent = new Intent(VaccineActivity.this, MenuVetActivity.class);
        startActivity(intent);
    }



    public void openCalendar(View view){
        Calendar calendar = Calendar.getInstance();
        int yearD = calendar.get(Calendar.YEAR);
        int monthD = calendar.get(Calendar.MONTH);
        int dayD = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(VaccineActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                textDate.setText(date);
            }

        }, yearD, monthD, dayD);
        dpd.show();
    }

    //addVacunacion registra el control medico de las vacunaciones como un objeto y valida lo ingresado.
    //Recibe la vista de vacunacion.xml
    public void addVacunacion(View view){
        String bovino = "toro 1";//selBovino spinner
        String enfermedad = textBovinoEnfermedad.getText().toString();
        String notas = textBovinoNotas.getText().toString();
        String fecha = textDate.getText().toString();

        if (TextUtils.isEmpty(bovino) && TextUtils.isEmpty(enfermedad) && TextUtils.isEmpty(fecha)) {

            Toast.makeText(VaccineActivity.this, "Ingrese todos los datos.", Toast.LENGTH_SHORT).show();
        } else {
            addDatatoFirebase(bovino, enfermedad, fecha, notas, view);
        }
    }

    //addDataToFirebase agrega a la base de datos el registro de vacuna
    private void addDatatoFirebase(String p_bovino, String p_enfermedad, String p_fecha, String p_notas, View view) {

        CollectionReference dbVacuna = db.collection("vacuna");
        vacuna nuevaVacuna = new vacuna(p_bovino, p_enfermedad, p_fecha, p_notas);

        dbVacuna.add(nuevaVacuna).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(VaccineActivity.this, "Registro de vacuna agregada.", Toast.LENGTH_SHORT).show();
                openVacunaRegresarActivity(view);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VaccineActivity.this, "Error al agregar registro de vacuna. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

}



