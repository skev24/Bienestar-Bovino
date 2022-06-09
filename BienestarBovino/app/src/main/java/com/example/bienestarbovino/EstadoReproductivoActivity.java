package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import control.Funciones;
import model.estadoReproductivo;
import model.venta;

public class EstadoReproductivoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Funciones {

    private Button btnRegresar, btnGuardar;
    private TextView ternera, destete, novilla, adulta;
    private EditText terneraFecha, terneraDestete, terneraNovillo, terneraAdulta;
    private HashMap<String, String> bovinosHash;
    private Spinner vacasSpinner;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String bovinoActual = "";
    private String vacaSpin = "";

    private List<venta> bovinos = new ArrayList<>();
    private String idFincaGlobal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_estado_reproductivo);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        vacasSpinner = findViewById(R.id.spinnerEstadoReproductivo);

        btnRegresar = findViewById(R.id.btnRegresarEstadoRep);
        btnGuardar = findViewById(R.id.buttonEstadoRepGuardar);
        ternera = findViewById(R.id.TextViewTernera);
        destete = findViewById(R.id.textViewDestetada);
        novilla = findViewById(R.id.textViewNovilla);
        adulta = findViewById(R.id.textViewAdulta);
        terneraFecha = findViewById(R.id.editTextTerneraFecha);
        terneraDestete = findViewById(R.id.editTextDesteteFecha);
        terneraNovillo = findViewById(R.id.editTextNovillaFecha);
        terneraAdulta = findViewById(R.id.editTextAdultaFecha);

        bovinosHash = new HashMap<>();

        btnGuardar.setEnabled(false); // valorar si cambiarlo
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goBack(); }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { guardarEstado(); }
        });

        terneraDestete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openCalendar(view,1); }
        });

        terneraNovillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openCalendar(view,2); }
        });

        terneraAdulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openCalendar(view,3); }
        });

        ternera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cambiarColor(view);
                //getFechas(view);
            }
        });

        cargarDatos();
        //spinnerBovino();

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
                ArrayAdapter<venta> arrayAdapter = new ArrayAdapter<>(EstadoReproductivoActivity.this, android.R.layout.simple_dropdown_item_1line, bovinos);
                vacasSpinner.setAdapter(arrayAdapter);
                vacasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                           @Override
                           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                               bovinoActual = bovinos.get(position).getbovino();
                               getFechas(bovinoActual, view);
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
        vacaSpin = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), tipoSpin, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void goBack() {
        Intent intent = new Intent(EstadoReproductivoActivity.this, ControlReproductivoActivity.class);
        startActivity(intent);
    }

    private void cambiarColorEstadoActual(View view){

        view.setBackgroundColor(Color.argb(128,122,210,104));
    }

    private void cambiarColor(View view){
        view.setBackgroundColor(Color.rgb(122,210,104));
    }

    // cargar datos de los bovinos
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

    public void getFechas(String bovino, View view){
        String id = bovinosHash.get(bovino);
        db.collection("bovino").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    if(documentSnapshot.getBoolean("estadoReproductivo") == false){
                        String fechaNacimiento = documentSnapshot.getString("fecha");
                        String fechaDestete = calcularFecha(fechaNacimiento, 1);
                        String fechaNovillo = calcularFecha(fechaNacimiento, 2);
                        String fechaAdulto = calcularFecha(fechaNacimiento, 4);

                        terneraFecha.setText(fechaNacimiento);
                        terneraDestete.setText(fechaDestete);
                        terneraNovillo.setText(fechaNovillo);
                        terneraAdulta.setText(fechaAdulto);

                        // ponga el campo EstadoReproductivo en true
                        db.collection("bovino").document(id).update("estadoReproductivo",true);
                        // guardo automaticamente
                        addDatatoFirebase(id, fechaNacimiento, fechaDestete, fechaNovillo, fechaAdulto);
                        // bloqueo el boton guardar hasta que no haya un cambio
                        btnGuardar.setEnabled(false);
                    }
                    else{
                        getFechasCreadas(id, view);
                    }
                }

            }
        });
    }

    private void getFechasCreadas(String id, View view){
        db.collection("estadoReproductivo").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String idBovino = qs.getString("idBovino");
                    if(idBovino.equals(id)){
                        String fechaNacimiento = qs.getString("nacimiento");
                        String fechaDestete = qs.getString("destete");
                        String fechaNovillo = qs.getString("novilla");
                        String fechaAdulto = qs.getString("adulta");

                        terneraFecha.setText(fechaNacimiento);
                        terneraDestete.setText(fechaDestete);
                        terneraNovillo.setText(fechaNovillo);
                        terneraAdulta.setText(fechaAdulto);
                        btnGuardar.setEnabled(false);
                        break;
                    }
                }
            }
        });

    }

    private void guardarEstado(){
        String id = bovinosHash.get(bovinoActual);
        db.collection("estadoReproductivo").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String idBovino = qs.getString("idBovino");
                    if(idBovino.equals(id)){
                        String destete = terneraDestete.getText().toString();
                        String novillo = terneraNovillo.getText().toString();
                        String adulta = terneraAdulta.getText().toString();

                        db.collection("estadoReproductivo").document(qs.getId()).update("destete", destete);
                        db.collection("estadoReproductivo").document(qs.getId()).update("novilla", novillo);
                        db.collection("estadoReproductivo").document(qs.getId()).update("adulta", adulta);
                        Toast.makeText(EstadoReproductivoActivity.this, "Estado actualizado.", Toast.LENGTH_SHORT).show();
                        btnGuardar.setEnabled(false);
                        break;
                    }
                }
            }
        });
    }


    private void addDatatoFirebase(String p_id, String p_nacimiento, String p_destete, String p_novillo, String p_adulta) {

        estadoReproductivo nuevoBovino = new estadoReproductivo(p_id, p_nacimiento, p_destete, p_novillo, p_adulta);

        db.collection("estadoReproductivo").add(nuevoBovino).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(EstadoReproductivoActivity.this, "Estado reproductivo guardado.", Toast.LENGTH_SHORT).show();
                goBack();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EstadoReproductivoActivity.this, "Error al guardar estado reproductivo. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String calcularFecha(String pFecha, int anno) {
        String fechaDestete = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(pFecha));
            c.add(Calendar.YEAR, anno);
            fechaDestete = sdf.format(c.getTime());
            //Toast.makeText(EstadoReproductivoActivity.this, fechaDestete, Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaDestete;
    }

    public void openCalendar(View view, int tipoFecha){

        Calendar calendar = Calendar.getInstance();
        int yearD = calendar.get(Calendar.YEAR);
        int monthD = calendar.get(Calendar.MONTH);
        int dayD = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(EstadoReproductivoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                if(tipoFecha == 1) terneraDestete.setText(date);
                else if(tipoFecha == 2) terneraNovillo.setText(date);
                else if(tipoFecha == 3) terneraAdulta.setText(date);
                btnGuardar.setEnabled(true);
            }
        }, yearD, monthD, dayD);
        dpd.show();
    }
}