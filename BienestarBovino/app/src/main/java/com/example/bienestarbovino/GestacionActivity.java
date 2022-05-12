package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import control.Funciones;
import model.estadoGestacion;
import model.estadoReproductivo;

public class GestacionActivity extends AppCompatActivity implements Funciones {

    private Button btnRegresar, btnGuardar;
    private EditText fecha;
    private TextView idGestacion, nameGestacion, razaGestacion;
    private HashMap<String, String> bovinosVacasHash, bovinosTorosHash;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String vacaActual = "test1";
    private String toroActual = "lalo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestacion);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btnRegresar = findViewById(R.id.btnRegresarGestacion);
        btnGuardar = findViewById(R.id.buttonGuardarGestacion);
        fecha = findViewById(R.id.editTextCalendarioGestacion);
        idGestacion = findViewById(R.id.textViewIdGestacion);
        nameGestacion = findViewById(R.id.textViewNombreGestacion);
        razaGestacion = findViewById(R.id.textViewRazaGestacion);

        bovinosVacasHash = new HashMap<>();
        bovinosTorosHash = new HashMap<>();

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openCalendar(view); }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { guardarEstado(); }
        });
        cargarDatos();
    }

    @Override
    public void goBack() {
        Intent intent = new Intent(GestacionActivity.this, ControlReproductivoActivity.class);
        startActivity(intent);
    }

    private void guardarEstado(){
        String idVaca = bovinosVacasHash.get(vacaActual);
        String idToro = bovinosTorosHash.get(toroActual);
        db.collection("estadoGestacion").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String idBovinoVaca = qs.getString("idBovinoVaca");
                    String idBovinoToro = qs.getString("idBovinoToro");
                    Boolean estadoGestacion = qs.getBoolean("estadoFinalizado");
                    String fechaGestacion = fecha.getText().toString();
                    String tipo = "monta"; // spinner
                    if(idBovinoVaca.equals(idVaca) && idBovinoToro.equals(idToro) && estadoGestacion.equals(false)){
                        db.collection("estadoGestacion").document(qs.getId()).update("fecha", fechaGestacion);
                        db.collection("estadoGestacion").document(qs.getId()).update("idBovinoToro", idToro);
                        db.collection("estadoGestacion").document(qs.getId()).update("tipo", tipo);
                        Toast.makeText(GestacionActivity.this, "Gestación actualizado.", Toast.LENGTH_SHORT).show();
                        btnGuardar.setEnabled(false);
                        break;
                    }
                    else {
                        db.collection("bovino").document(idVaca).update("EstadoGestacion", true);
                        db.collection("bovino").document(idToro).update("EstadoGestacion", true); // se puede quitar porque seguro no se usa
                        addDatatoFirebase(idVaca, idToro, tipo, fechaGestacion);
                    }
                }
            }
        });
    }

    private void addDatatoFirebase(String idBovinoVaca, String idBovinoToro, String tipo, String fecha) {

        estadoGestacion nuevaGestacion = new estadoGestacion(idBovinoVaca, idBovinoToro, tipo, fecha);

        db.collection("estadoGestacion").add(nuevaGestacion).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(GestacionActivity.this, "Estado de gestación guardado.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GestacionActivity.this, "Error al guardar estado de gestación. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
        btnGuardar.setEnabled(false);
    }

    public void openCalendar(View view){

        Calendar calendar = Calendar.getInstance();
        int yearD = calendar.get(Calendar.YEAR);
        int monthD = calendar.get(Calendar.MONTH);
        int dayD = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(GestacionActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                fecha.setText(date);
                btnGuardar.setEnabled(true);
            }
        }, yearD, monthD, dayD);
        dpd.show();
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
        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String finca = qs.getString("fincaId");
                    if(finca.equals(idFinca)){
                        String name = qs.getString("name");
                        String id = qs.getId();
                        Boolean sexo = qs.getBoolean("sexo");
                        if(sexo) bovinosTorosHash.put(name,id);
                        else bovinosVacasHash.put(name,id);
                    }
                }
                getInfoVaca();
            }
        });
    }

    public void getInfoVaca(){
        String id = bovinosVacasHash.get(vacaActual);
        db.collection("bovino").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String name = documentSnapshot.getString("name");
                    String idVaca = documentSnapshot.getString("id");
                    String raza = documentSnapshot.getString("raza");

                    idGestacion.setText(idVaca);
                    nameGestacion.setText(name);
                    razaGestacion.setText(raza);

                }
            }
        });
    }

}