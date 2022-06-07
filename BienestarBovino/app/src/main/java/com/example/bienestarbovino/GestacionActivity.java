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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import control.Funciones;
import model.estadoGestacion;
import model.estadoReproductivo;
import model.venta;

public class GestacionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Funciones {

    private Button btnRegresar, btnGuardar;
    private EditText fecha;
    private TextView idGestacion, nameGestacion, razaGestacion;
    private HashMap<String, String> bovinosVacasHash, bovinosTorosHash;
    private Spinner vacasSpinner, torosSpinner, tipoGestacionSpinner;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String vacaActual = "test1";
    private String toroActual = "lalo";
    private String vacaSpin = "";
    private String toroSpin = "";
    private String tipoSpin = "";

    private List<venta> bovinos = new ArrayList<>();
    private List<venta> bovinosToro = new ArrayList<>();
    private String idFincaGlobal = "";
    private String bovinoSeleccionado = "";
    private String bovinoSeleccionadoToro = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestacion);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        vacasSpinner = findViewById(R.id.spinnerGestacionVaca);
        torosSpinner = findViewById(R.id.spinnerGestacionToro);
        tipoGestacionSpinner = findViewById(R.id.spinnerGestacionTipo);

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

//        cargarSpinners();
        cargarDatos();
//        spinnerBovinoVaca();
//        spinnerBovinoToro();

    }
    public void spinnerBovinoVaca(){

        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String name = qs.getString("name");
                    String raza = qs.getString("raza");
                    String id = qs.getString("id");
                    Boolean sexo = qs.getBoolean("sexo");
                    if(qs.getString("fincaId").equals(idFincaGlobal) && sexo.equals(Boolean.FALSE))
                        bovinos.add(new venta(name,id,raza));
                }
                ArrayAdapter<venta> arrayAdapter = new ArrayAdapter<>(GestacionActivity.this, android.R.layout.simple_dropdown_item_1line, bovinos);
                vacasSpinner.setAdapter(arrayAdapter);
                vacasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                         @Override
                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                             //bovinoSeleccionado = parent.getItemAtPosition(position).toString();
                             idGestacion.setText(bovinos.get(position).getmonto());
                             nameGestacion.setText( bovinos.get(position).getbovino());
                             bovinoSeleccionado = bovinos.get(position).getbovino();
                             razaGestacion.setText(bovinos.get(position).getfecha());
                         }

                         @Override
                         public void onNothingSelected(AdapterView<?> parent) {

                         }
                     }

                );

            }
        });
    }

    public void spinnerBovinoToro(){

        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String name = qs.getString("name");
                    String raza = qs.getString("raza");
                    String id = qs.getString("id");
                    Boolean sexo = qs.getBoolean("sexo");
                    if(qs.getString("fincaId").equals(idFincaGlobal) && sexo.equals(Boolean.TRUE))
                        bovinosToro.add(new venta(name,id,raza));
                }
                ArrayAdapter<venta> arrayAdapter = new ArrayAdapter<>(GestacionActivity.this, android.R.layout.simple_dropdown_item_1line, bovinosToro);
                torosSpinner.setAdapter(arrayAdapter);
                torosSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                           @Override
                           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                               //bovinoSeleccionado = parent.getItemAtPosition(position).toString();
                               //idGestacion.setText("Identificación:  " + bovinos.get(position).getmonto());
                               //nameGestacion.setText("Nombre:  " + bovinos.get(position).getbovino());
                               bovinoSeleccionadoToro = bovinosToro.get(position).getbovino();
                               //razaGestacion.setText("Raza:  " + bovinos.get(position).getfecha());
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

        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(this,
                R.array.Tipo, android.R.layout.simple_spinner_item);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoGestacionSpinner.setAdapter(adapterTipo);
        tipoGestacionSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //vacaSpin = parent.getItemAtPosition(position).toString();
        //toroSpin = parent.getItemAtPosition(position).toString();
        tipoSpin = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), tipoSpin, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                        db.collection("bovino").document(idVaca).update("estadoGestacion", true);
                        db.collection("bovino").document(idToro).update("estadoGestacion", true); // se puede quitar porque seguro no se usa
                        addDatatoFirebase(idVaca, idToro, tipo, fechaGestacion);
                        break;
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
                        if(sexo) bovinosTorosHash.put(name,id);
                        else bovinosVacasHash.put(name,id);
                    }
                }
                getInfoVaca();
            }
        });
        spinnerBovinoVaca();
        spinnerBovinoToro();
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