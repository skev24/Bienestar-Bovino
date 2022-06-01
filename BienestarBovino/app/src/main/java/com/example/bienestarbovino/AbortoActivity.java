package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import java.util.HashMap;

import control.Funciones;
import model.bovino;
import model.parto;

public class AbortoActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, Funciones {

    private Button btnRegresar, btnGuardar;
    private EditText fecha;
    private TextView idVacaAborto, nameVacaAborto, razaVacaAborto;
    private Spinner vacasSpinner;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private HashMap<String, String> bovinosVacasHash;

    private String vacaActual = "test1";
    private String idFincaGlobal = "";
    private String vacaSpin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aborto);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        vacasSpinner = findViewById(R.id.spinnerVacaAborto);

        btnRegresar = findViewById(R.id.btnAbortoRegresar);
        btnGuardar = findViewById(R.id.buttonAbortoRegister);
        fecha = findViewById(R.id.editFechaAborto);
        idVacaAborto = findViewById(R.id.textViewIdVacaAborto);
        nameVacaAborto = findViewById(R.id.textViewNombreVacaAborto);
        razaVacaAborto = findViewById(R.id.textViewRazaVacaAborto);

        bovinosVacasHash = new HashMap<>();

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openCalendar(view); }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goBack(); }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarEstado();
            }
        });
//        cargarSpinners();
//        cargarDatos();
    }

    public void cargarSpinners(){
        ArrayAdapter<CharSequence> adapterVacas = ArrayAdapter.createFromResource(this,
                R.array.Vacas, android.R.layout.simple_spinner_item);
        adapterVacas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vacasSpinner.setAdapter(adapterVacas);
        vacasSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        vacaSpin = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), tipoSpin, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void goBack(){
        Intent intent = new Intent(AbortoActivity.this, ControlReproductivoActivity.class);
        startActivity(intent);
    }

    public void openCalendar(View view){
        Calendar calendar = Calendar.getInstance();
        int yearD = calendar.get(Calendar.YEAR);
        int monthD = calendar.get(Calendar.MONTH);
        int dayD = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(AbortoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                fecha.setText(date);
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
        idFincaGlobal = idFinca;
        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String finca = qs.getString("fincaId");
                    if(finca.equals(idFinca)){
                        String name = qs.getString("name");
                        String id = qs.getId();
                        Boolean sexo = qs.getBoolean("sexo");
                        Boolean enGestacion = qs.getBoolean("EstadoGestacion");
                        if(!sexo && enGestacion) bovinosVacasHash.put(name,id);
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

                    idVacaAborto.setText(idVaca);
                    nameVacaAborto.setText(name);
                    razaVacaAborto.setText(raza);
                }
            }
        });
    }

    private void guardarEstado(){
        String idVaca = bovinosVacasHash.get(vacaActual);
        db.collection("estadoGestacion").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
//                    String idBovinoVaca = qs.getString("idBovinoVaca");
//                    String idBovinoToro = qs.getString("idBovinoToro");
//                    Boolean estadoGestacion = qs.getBoolean("estadoFinalizado");
//                    Boolean sexo = false; // spinnerGenero
//                    String idNuevoBovino = idParto.getText().toString();
//                    String nombreNuevoBovino = nombreParto.getText().toString();
//                    String pesoNuevoBovino = pesoParto.getText().toString();
//                    String fechaN = fecha.getText().toString();
//                    String raza = razaVacaParto.getText().toString();
//
//                    if(idBovinoVaca.equals(idVaca) && estadoGestacion.equals(false)){
//                        db.collection("estadoGestacion").document(qs.getId()).update("estadoFinalizado", true);
//                        db.collection("bovino").document(idVaca).update("EstadoGestacion", false);
//
//                        addDatatoFirebase(nombreNuevoBovino, idNuevoBovino, raza, idBovinoToro, idVaca, fechaN, pesoNuevoBovino, idFincaGlobal, sexo);
//                        btnGuardar.setEnabled(false);
//                        break;
//                    }
                }
            }
        });
    }

    private void addDatatoFirebase(String name, String id, String raza, String padre, String madre, String fecha, String pesoNacimiento, String fincaId, Boolean sexo) {

        // String idVaca, String idCria, String fecha, String sexo
        bovino nuevoBovino = new bovino(name, id, raza, padre, madre, fecha, pesoNacimiento, fincaId, sexo);

        db.collection("bovino").add(nuevoBovino).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AbortoActivity.this, "Nuevo aborto guardado.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AbortoActivity.this, "Error al guardar el aborto. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
        guardarAborto(id, name, madre, fincaId, fecha, sexo);
        btnGuardar.setEnabled(false);
    }

    private void guardarAborto(String id, String name, String madre, String fincaId, String fecha, Boolean sexo){
        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
//                    String finca = qs.getString("fincaId");
//                    String nameBovino = qs.getString("name");
//                    String madreBovino = qs.getString("madre");
//                    String fechaBovino = qs.getString("fecha");
//                    String idBov = qs.getString("id");
//                    if(idBov.equals(id) && finca.equals(fincaId) && nameBovino.equals(name) && madreBovino.equals(madre) && fechaBovino.equals(fecha)){
//                        String idBovino = qs.getId();
//                        addPartotoFirebase(idBovino, madre, fecha, sexo);
//                    }
                }
            }
        });
    }

    private void addPartotoFirebase(String id, String madre, String fecha, Boolean sexo) {

        parto nuevoParto = new parto(madre, id, fecha, sexo);

        db.collection("partos").add(nuevoParto).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AbortoActivity.this, "Nuevo aborto guardado.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AbortoActivity.this, "Error al guardar aborto. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
        btnGuardar.setEnabled(false);
    }
}