package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import control.Funciones;
import model.produccionPeso;
import model.venta;

public class WeightProductionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Funciones {

    private EditText textDate;
    private Button buttonPesoRegresarClass;
    private Spinner vacasSpinner, dietaSpinner;
    private EditText textPesoBovino, textPesoNotas;
    private Button buttonAddPeso;
    private HashMap<String, String> bovinosHash;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String vacaSpin = "";
    private String dietaSpin = "";

    private List<venta> bovinos = new ArrayList<>();
    private String bovinoSeleccionado = "";
    private String idFincaGlobal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produccion_peso);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        textDate = findViewById(R.id.editFechaPeso);
        buttonPesoRegresarClass = findViewById(R.id.buttonPesoRegresar);
        vacasSpinner = findViewById(R.id.spinnerBovinoPeso);
        dietaSpinner = findViewById(R.id.spinnerDieta);
        textPesoBovino = findViewById(R.id.editPesoVaca);
        textPesoNotas = findViewById(R.id.editTextNotasPeso);
        buttonAddPeso = findViewById(R.id.buttonPesoRegister);
        bovinosHash = new HashMap<>();

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

        cargarDatos();
        cargarSpinners();
//        spinnerBovino();
    }

    public void spinnerBovino(){

        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String name = qs.getString("name");
                    String raza = qs.getString("raza");
                    String id = qs.getString("id");
                    if(qs.getString("fincaId").equals(idFincaGlobal) && qs.getBoolean("activoEnFinca").equals(Boolean.TRUE))
                        bovinos.add(new venta(name,id,raza));
                }
                ArrayAdapter<venta> arrayAdapter = new ArrayAdapter<>(WeightProductionActivity.this, android.R.layout.simple_dropdown_item_1line, bovinos);
                vacasSpinner.setAdapter(arrayAdapter);
                vacasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                       @Override
                       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                           bovinoSeleccionado = bovinos.get(position).getbovino();
                       }

                       @Override
                       public void onNothingSelected(AdapterView<?> parent) {

                       }
                   }
                );

            }
        });
    }

    public void cargarSpinners(){

        ArrayAdapter<CharSequence> adapterDieta = ArrayAdapter.createFromResource(this,
                R.array.Dieta, android.R.layout.simple_spinner_item);
        adapterDieta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dietaSpinner.setAdapter(adapterDieta);
        dietaSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        vacaSpin = parent.getItemAtPosition(position).toString();
        dietaSpin = dietaSpinner.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), tipoSpin, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        String bovino = bovinoSeleccionado;//selBovino spinner
        String peso = textPesoBovino.getText().toString();
        String notas = textPesoNotas.getText().toString();
        String fecha = textDate.getText().toString();
        String dieta = dietaSpin;
        String id = bovinosHash.get(bovinoSeleccionado);

        if (TextUtils.isEmpty(bovino) && TextUtils.isEmpty(peso) && TextUtils.isEmpty(fecha)) {
            Toast.makeText(WeightProductionActivity.this, "Ingrese todos los datos.", Toast.LENGTH_SHORT).show();
        } else {
            addDatatoFirebase(bovino, peso, fecha, dieta, notas, id, view);
        }
    }

    //addDataToFirebase agrega a la base de datos el registro de vacuna
    private void addDatatoFirebase(String p_bovino, String p_peso, String p_fecha, String p_dieta, String p_notas, String id, View view) {

        CollectionReference dbProduccion = db.collection("produccionPeso");
        produccionPeso nuevoPesaje = new produccionPeso(p_bovino, p_peso, p_fecha, p_dieta, p_notas, id);

        dbProduccion.add(nuevoPesaje).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

    public void cargarDatos(){
        db.collection("finca").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String idFinca = "";
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String user = qs.getString("user");
                    if(user.equals(mAuth.getCurrentUser().getUid())){
                        idFinca = qs.getId();
                        break;
                    }
                }
                getDataBovino(idFinca);
            }
        });
    }

    public void getDataBovino(String idFinca){
        idFincaGlobal = idFinca;
        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String finca = qs.getString("fincaId");
                    if(finca.equals(idFinca) && qs.getBoolean("activoEnFinca").equals(Boolean.TRUE)){
                        String name = qs.getString("name");
                        String id = qs.getId();
                        bovinosHash.put(name,id);
                    }
                }
            }
        });
        spinnerBovino();
    }

}