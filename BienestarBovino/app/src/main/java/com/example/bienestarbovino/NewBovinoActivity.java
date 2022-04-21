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

public class NewBovinoActivity extends AppCompatActivity {

    private EditText textNameBovino, textIdBovino, textAlNacer, textAlDestete, text12Meses;
    private TextView textDate;
    private Spinner selRaza, selMadre, selPadre;
    private Button buttonOpenCalendar;
    private Button buttonAddBovino, buttonBack;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String idFinca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbovino);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        textNameBovino = findViewById(R.id.editTextNameNewBovino);
        textIdBovino = findViewById(R.id.editTextIdNewBovino);
        textAlNacer = findViewById(R.id.editTextNacimientoNewBovino);
        textAlDestete = findViewById(R.id.editTextDesteteNewBovino);
        text12Meses = findViewById(R.id.editTextMesesNewBovino);
        textDate = findViewById(R.id.textViewDateNewBovino);
        selRaza = findViewById(R.id.spinnerRazaNewBovino);
        selMadre = findViewById(R.id.spinnerMadreNewBovino);
        selPadre = findViewById(R.id.spinnerPadreNewBovino);
        buttonOpenCalendar = findViewById(R.id.buttonOpenCalendarNewBovino);
        buttonAddBovino = findViewById(R.id.buttonAddNewBovino);
        buttonBack = findViewById(R.id.buttonBackNewBovino);

        buttonOpenCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar(view);
            }
        });

        buttonAddBovino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewBovino(view);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentaRegresarActivity();
            }
        });
    }

    public void openVentaRegresarActivity(){
        Intent intent = new Intent(NewBovinoActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void openCalendar(View view){
        Calendar calendar = Calendar.getInstance();
        int yearD = calendar.get(Calendar.YEAR);
        int monthD = calendar.get(Calendar.MONTH);
        int dayD = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(NewBovinoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                textDate.setText(date);
            }

        }, yearD, monthD, dayD);
        dpd.show();
    }

    public void addNewBovino(View view){
        String name = textNameBovino.getText().toString();
        String id = textIdBovino.getText().toString();
        String raza = "Holstein";//selRaza spinner
        String madre = "-";//selMadre spinner
        String padre = "-";//selPadre spinner
        String fecha = textDate.getText().toString();
        String pesoNacimiento = textAlNacer.getText().toString();
        String pesonDestete = textAlDestete.getText().toString();
        String pesoMeses = text12Meses.getText().toString();

        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(id) && TextUtils.isEmpty(fecha) && TextUtils.isEmpty(pesoNacimiento)
                && TextUtils.isEmpty(pesonDestete) && TextUtils.isEmpty(pesoMeses)) { // && TextUtils.isEmpty(raza) && TextUtils.isEmpty(madre) && TextUtils.isEmpty(padre)

            Toast.makeText(NewBovinoActivity.this, "Ingrese todos los datos.", Toast.LENGTH_SHORT).show();
        } else {
            addDatatoFirebase(name, id, raza, madre, padre, fecha, pesoNacimiento, pesonDestete, pesoMeses);
        }
    }

    private void addDatatoFirebase(String p_name, String p_id, String p_raza, String p_madre, String p_padre,
                                   String p_fecha, String p_Nacimiento, String p_Destete, String p_Meses) {

        CollectionReference dbBovino = db.collection("bovino");
        buscarIdFinca();
        bovino nuevoBovino = new bovino(p_name, p_id, p_raza, p_padre, p_madre, p_fecha, p_Nacimiento,
                p_Destete, p_Meses, idFinca);

        dbBovino.add(nuevoBovino).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(NewBovinoActivity.this, "Bovino agregado.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewBovinoActivity.this, "Error al agregar bovino. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Se puede poner en algun lugar para que todos lo usen
    public void buscarIdFinca(){

        db.collection("finca").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int state = 0;
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){

                    String user = qs.getString("user");
                    if(user.equals(mAuth.getCurrentUser().getUid())){
                        idFinca = user;
                        break;
                    }
                }
            }
        });

    }
}