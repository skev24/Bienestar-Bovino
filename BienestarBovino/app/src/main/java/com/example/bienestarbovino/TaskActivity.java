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
import model.tarea;
import model.venta;

public class TaskActivity extends AppCompatActivity implements Funciones {

    private TextView textDate, textDescripcion;
    private Spinner selEncargado;
    private Button buttonAddTask, buttonBack;

    private String personalSeleccionado = "";
    private String idFincaGlobal = "";
    private List<venta> personal = new ArrayList<>();
    private HashMap<String, String> personalHash;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_tarea);

        textDate = findViewById(R.id.entryFechaTarea);
        textDescripcion = findViewById(R.id.editDescripcionTarea);
        selEncargado = findViewById(R.id.spinnerEncargadoTarea);
        buttonAddTask = findViewById(R.id.buttonTaskRegister);
        buttonBack = findViewById(R.id.buttonTareaRegresar);

        personalHash = new HashMap<>();

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar(view);
            }
        });

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTask(view);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        cargarDatos();

    }

    public void openCalendar(View view){
        Calendar calendar = Calendar.getInstance();
        int yearD = calendar.get(Calendar.YEAR);
        int monthD = calendar.get(Calendar.MONTH);
        int dayD = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(TaskActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                textDate.setText(date);
            }
        }, yearD, monthD, dayD);
        dpd.show();
    }

    public void spinnerPersonal(){

        db.collection("personal").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String name = qs.getString("nombre");
                    String raza = qs.getString("apellido");
                    String id = qs.getString("id");
                    String completeName = name + " " + raza;
                    if(qs.getString("idFinca").equals(idFincaGlobal) && qs.getBoolean("activoEnFinca").equals(Boolean.TRUE))
                        personal.add(new venta(completeName,id,raza));
                }
                ArrayAdapter<venta> arrayAdapter = new ArrayAdapter<>(TaskActivity.this, android.R.layout.simple_dropdown_item_1line, personal);
                selEncargado.setAdapter(arrayAdapter);
                selEncargado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                           @Override
                           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                               personalSeleccionado = parent.getItemAtPosition(position).toString();
                           }

                           @Override
                           public void onNothingSelected(AdapterView<?> parent) {

                           }
                       }

                );

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
        db.collection("personal").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String finca = qs.getString("idFinca");
                    if(finca.equals(idFinca) && qs.getBoolean("activoEnFinca").equals(Boolean.TRUE)){
                        String name = qs.getString("nombre");
                        String lastname = qs.getString("apellido");
                        String id = qs.getId();
                        String completeName = name + " " + lastname;
                        personalHash.put(completeName, id);
                    }
                }
                //getInfoVaca();
            }
        });
        spinnerPersonal();
    }

    public void goBack(){
        Intent intent = new Intent(TaskActivity.this, ListTaskActivity.class);
        startActivity(intent);
    }


    public void addNewTask(View view){
        String fecha = textDate.getText().toString();;
        String descripcion = textDescripcion.getText().toString();
        String encargadoTarea = personalSeleccionado;
        String id = idFincaGlobal;

        if (TextUtils.isEmpty(fecha) && TextUtils.isEmpty(encargadoTarea) && TextUtils.isEmpty(descripcion)) {
            Toast.makeText(TaskActivity.this, "Ingrese todos los datos.", Toast.LENGTH_SHORT).show();
        }else{
            addDatatoFirebase(fecha, encargadoTarea, descripcion, id);
            };
        }


    private void addDatatoFirebase(String p_fecha, String p_encargado, String p_descripcion, String idFinca) {

        tarea nuevaTarea = new tarea(p_fecha, p_encargado, p_descripcion, idFinca);

        db.collection("tarea").add(nuevaTarea).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(TaskActivity.this, "Tarea agregada.", Toast.LENGTH_SHORT).show();
                goBack();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TaskActivity.this, "Error al agregar tarea. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

