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
import model.vacuna;
import model.venta;

public class VaccineActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Funciones {

    private TextView textDate;
    private Button buttonVacunaRegresarClass;
    private Spinner setBovino;
    private EditText textBovinoEnfermedad, textBovinoNotas;
    private Button buttonAddVacuna;
    private HashMap<String, String> bovinosHash;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String bovinoSpin = "";

    private List<venta> bovinos = new ArrayList<>();
    private String bovinoSeleccionado = "";
    private String idFincaGlobal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacunacion);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bovinosHash = new HashMap<>();

        textDate = findViewById(R.id.entryId);
        buttonVacunaRegresarClass = findViewById(R.id.btnRegresarPersonal);
        setBovino = findViewById(R.id.spinnerType);
        textBovinoEnfermedad = findViewById(R.id.entryName);
        textBovinoNotas = findViewById(R.id.entryLastname);
        buttonAddVacuna = findViewById(R.id.buttonGuardarPersonal);

        buttonVacunaRegresarClass.setOnClickListener(new View.OnClickListener() {
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

        buttonAddVacuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVacunacion(view);
            }
        });

        cargarDatos();
//        spinnerBovino();
    }

    public void goBack(){
        Intent intent = new Intent(VaccineActivity.this, MenuVetActivity.class);
        startActivity(intent);
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
                ArrayAdapter<venta> arrayAdapter = new ArrayAdapter<>(VaccineActivity.this, android.R.layout.simple_dropdown_item_1line, bovinos);
                setBovino.setAdapter(arrayAdapter);
                setBovino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        bovinoSpin = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), tipoSpin, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        String bovino = bovinoSeleccionado;//selBovino spinner
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
                goBack();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VaccineActivity.this, "Error al agregar registro de vacuna. \n" + e, Toast.LENGTH_SHORT).show();
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



