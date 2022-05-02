package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
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

import java.util.HashMap;

import control.Funciones;
import model.bovino;
import model.estadoReproductivo;

public class EstadoReproductivoActivity extends AppCompatActivity implements Funciones {

    private Button btnRegresar, btnGuardar;
    private TextView ternera, destete, novilla, adulta;
    private EditText terneraFecha, terneraDestete, terneraNovillo, terneraAdulta;
    private HashMap<String, String> bovinosHash;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String bovinoActual = "test1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_estado_reproductivo);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

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

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarEstado();
            }
        });

        ternera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cambiarColor(view);
                getFechas(view);
            }
        });

        cargarDatos();
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
        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String finca = qs.getString("fincaId");
                    if(finca.equals(idFinca)){
                        String name = qs.getString("name");
                        String id = qs.getId();
                        bovinosHash.put(name,id);
                    }
                }
            }
        });
    }

    public void getFechas(View view){
        String id = bovinosHash.get(bovinoActual);
        db.collection("bovino").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    if(documentSnapshot.getBoolean("EstadoReproductivo") == false){
                        String fechaNacimiento = documentSnapshot.getString("fecha");
                        String fechaDestete = calcularDestete(fechaNacimiento);
                        String fechaNovillo = calcularNovilla(fechaNacimiento);
                        String fechaAdulto = calcularAdulta(fechaNacimiento);

                        terneraFecha.setText(fechaNacimiento);
                        terneraDestete.setText(fechaDestete);
                        terneraNovillo.setText(fechaNovillo);
                        terneraAdulta.setText(fechaAdulto);

                        // ponga el campo EstadoReproductivo en true
                        db.collection("bovino").document(id).update("EstadoReproductivo",true);
                        // guardo automaticamente
                        addDatatoFirebase(id, fechaNacimiento, fechaDestete, fechaNovillo, fechaAdulto);
                        // bloqueo el boton guardar hasta que no haya un cambio
                        btnGuardar.setEnabled(false);
                    }
                    else{
                        Toast.makeText(EstadoReproductivoActivity.this, "Ingreso aca.", Toast.LENGTH_SHORT).show();
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
                        //btnGuardar.setEnabled(false);
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

                        db.collection("estadoReproductivo").document(qs.getId()).update("destete", "prueba2");
                        db.collection("estadoReproductivo").document(qs.getId()).update("novilla", novillo);
                        db.collection("estadoReproductivo").document(qs.getId()).update("adulta", adulta);
                        Toast.makeText(EstadoReproductivoActivity.this, "Estado actualizado.", Toast.LENGTH_SHORT).show();
                        //btnGuardar.setEnabled(false);
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
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EstadoReproductivoActivity.this, "Error al guardar estado reproductivo. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String calcularDestete(String pFecha){
        return "prueba";
    }

    private String calcularNovilla(String pFecha){
        return "prueba";
    }

    private String calcularAdulta(String pFecha){
        return "prueba";
    }
}