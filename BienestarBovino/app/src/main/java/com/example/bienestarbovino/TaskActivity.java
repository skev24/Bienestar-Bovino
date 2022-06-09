package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
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

import control.Funciones;
import model.Tarea;
import model.personal;

public class TaskActivity extends AppCompatActivity implements Funciones {

    private TextView textDate, textDescripcion;
    private Spinner selEncargado;
    private Button buttonAddTask, buttonBack;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_tarea);

        textDate = findViewById(R.id.entryFechaTarea);
        textDescripcion = findViewById(R.id.editDescripcionTarea);
        selEncargado = findViewById(R.id.spinnerEncargadoTarea);
        buttonAddTask = findViewById(R.id.buttonTareaRegresar);
        buttonBack = findViewById(R.id.buttonTaskRegister);


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

    public void goBack(){
        Intent intent = new Intent(TaskActivity.this, MenuActivity.class);
        startActivity(intent);
    }


    public void addNewTask(View view){
        String fecha = textDate.getText().toString();;
        String descripcion = textDescripcion.getText().toString();
        String encargadoTarea = "Ignacio Araya";
        boolean concluida = false;

        if (TextUtils.isEmpty(fecha) && TextUtils.isEmpty(encargadoTarea) && TextUtils.isEmpty(descripcion)) {
            Toast.makeText(TaskActivity.this, "Ingrese todos los datos.", Toast.LENGTH_SHORT).show();
        }else{

            addDatatoFirebase(fecha, encargadoTarea, descripcion, concluida);

            });
        }
    }


    private void addDatatoFirebase(String p_fecha, String p_encargado, String p_descripcion, String p_concluida) {

        CollectionReference dbPersonal = db.collection("tarea");

        db.collection("finca").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String idFinca = "";
                for (DocumentSnapshot qs : queryDocumentSnapshots.getDocuments()) {
                    String user = qs.getString("user");
                    if (user.equals(mAuth.getCurrentUser().getUid())) {
                        idFinca = qs.getId();
                        break;
                    }
                }
                Tarea nuevoTarea = new Tarea(p_fecha, p_encargado, p_descripcion, p_concluida, idFinca);

                dbPersonal.add(nuevoTarea).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(TaskActivity.this, "Tarea agregada.", Toast.LENGTH_SHORT).show();
                        goBack();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PersonalActivity.this, "Error al agregar tarea. \n" + e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    }

