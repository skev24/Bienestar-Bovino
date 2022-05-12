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

import model.tratamiento;

public class TreatmentActivity extends AppCompatActivity {

    private TextView textDate;
    private Button buttonTratamientoRegresarClass;
    private Spinner setBovino;
    private EditText textDiasTratamiento, textBovinoNotas, textBovinoDiagnostico;
    private Button buttonAddTratamiento;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacunacion);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        textDate = findViewById(R.id.entryFechaTratamiento);
        buttonTratamientoRegresarClass = findViewById(R.id.btnRegresarTratamiento);
        setBovino = findViewById(R.id.spinnerBovinoVacunacion);
        textBovinoDiagnostico = findViewById(R.id.entryDiagnosticoTratamiento);
        textBovinoNotas = findViewById(R.id.editNotasTratamiento);
        textDiasTratamiento = findViewById(R.id.editNotasTratamiento);
        buttonAddTratamiento = findViewById(R.id.buttonGuardarTratamiento);
        buttonTratamientoRegresarClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTratamientoRegresarActivity(view);
            }
        });
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar(view);
            }
        });

        buttonAddTratamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTratamiento(view);
            }
        });
    }

    public void openTratamientoRegresarActivity(View view){
        Intent intent = new Intent(TreatmentActivity.this, MenuVetActivity.class);
        startActivity(intent);
    }



    public void openCalendar(View view){
        Calendar calendar = Calendar.getInstance();
        int yearD = calendar.get(Calendar.YEAR);
        int monthD = calendar.get(Calendar.MONTH);
        int dayD = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(TreatmentActivity.this, new DatePickerDialog.OnDateSetListener() {
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
    public void addTratamiento(View view){
        String bovino = "toro 1";//selBovino spinner
        String diagnostico = textBovinoDiagnostico.getText().toString();
        String diasTratamiento = textDiasTratamiento.getText().toString();
        String notas = textBovinoNotas.getText().toString();
        String fecha = textDate.getText().toString();

        if (TextUtils.isEmpty(bovino) && TextUtils.isEmpty(diagnostico) && TextUtils.isEmpty(fecha)) {

            Toast.makeText(TreatmentActivity.this, "Ingrese todos los datos.", Toast.LENGTH_SHORT).show();
        } else {
            addDatatoFirebase(bovino, fecha, diagnostico, diasTratamiento, notas, view);
        }
    }

    //addDataToFirebase agrega a la base de datos el registro de vacuna
    private void addDatatoFirebase(String p_bovino, String p_fecha, String p_diagnostico, String p_diasTratamiento, String p_notas, View view) {

        CollectionReference dbTratamiento = db.collection("tratamiento");
        tratamiento nuevoTratamiento = new tratamiento(p_bovino, p_fecha, p_diagnostico, p_diasTratamiento, p_notas);

        dbTratamiento.add(nuevoTratamiento).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(TreatmentActivity.this, "Registro de vacuna agregada.", Toast.LENGTH_SHORT).show();
                openTratamientoRegresarActivity(view);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TreatmentActivity.this, "Error al agregar registro de vacuna. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
