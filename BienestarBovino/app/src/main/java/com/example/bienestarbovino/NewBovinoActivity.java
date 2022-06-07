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
import model.bovino;
import model.finca;
import model.venta;

public class NewBovinoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Funciones {

    private EditText textNameBovino, textIdBovino, textAlNacer, textAlDestete, text12Meses;
    private TextView textDate;
    private Spinner selRaza, selGenero, selMadre, selPadre;
    private Button buttonAddBovino, buttonBack;
    private HashMap<String, String> bovinosVacasHash, bovinosTorosHash;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String razaSpin = "";
    private String sexoSpin = "";
    private String vacaSpin = "";
    private String toroSpin = "";

    private List<venta> bovinosP = new ArrayList<>();//Padre
    private List<venta> bovinosM = new ArrayList<>();//Madre
    private String bovinoSeleccionadoP = "";//Madre
    private String bovinoSeleccionadoM = "";//padre
    private String idFincaGlobal = "";


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
        selGenero = findViewById(R.id.spinnerGeneroNewBovino);
        selMadre = findViewById(R.id.spinnerMadreNewBovino);
        selPadre = findViewById(R.id.spinnerPadreNewBovino);
        buttonAddBovino = findViewById(R.id.buttonAddNewBovino);
        buttonBack = findViewById(R.id.buttonBackNewBovino);
        bovinosVacasHash = new HashMap<>();
        bovinosTorosHash = new HashMap<>();

        textDate.setOnClickListener(new View.OnClickListener() {
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
                goBack();
            }
        });

        cargarSpinners();
        cargarDatos();
//        spinnerBovinoMadre();
//        spinnerBovinoPadre();
    }

    public void spinnerBovinoMadre(){

        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String name = qs.getString("name");
                    String raza = qs.getString("raza");
                    String id = qs.getString("id");
                    Boolean sexo = qs.getBoolean("sexo");
                    if(qs.getString("fincaId").equals(idFincaGlobal) && sexo.equals(Boolean.FALSE) &&
                            qs.getBoolean("activoEnFinca").equals(Boolean.TRUE))
                        bovinosM.add(new venta(name,id,raza));
                }
                ArrayAdapter<venta> arrayAdapter = new ArrayAdapter<>(NewBovinoActivity.this, android.R.layout.simple_dropdown_item_1line, bovinosM);
                selMadre.setAdapter(arrayAdapter);
                selMadre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                       @Override
                       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                           //bovinoSeleccionado = parent.getItemAtPosition(position).toString();
                           //idGestacion.setText("Identificación:  " + bovinos.get(position).getmonto());
                           //nameGestacion.setText("Nombre:  " + bovinos.get(position).getbovino());
                           bovinoSeleccionadoM = bovinosM.get(position).getbovino();
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

    public void spinnerBovinoPadre(){

        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String name = qs.getString("name");
                    String raza = qs.getString("raza");
                    String id = qs.getString("id");
                    Boolean sexo = qs.getBoolean("sexo");
                    if(qs.getString("fincaId").equals(idFincaGlobal) && sexo.equals(Boolean.TRUE))
                        bovinosP.add(new venta(name,id,raza));
                }
                ArrayAdapter<venta> arrayAdapter = new ArrayAdapter<>(NewBovinoActivity.this, android.R.layout.simple_dropdown_item_1line, bovinosP);
                selPadre.setAdapter(arrayAdapter);
                selPadre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                       @Override
                       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                           //bovinoSeleccionado = parent.getItemAtPosition(position).toString();
                           //idGestacion.setText("Identificación:  " + bovinos.get(position).getmonto());
                           //nameGestacion.setText("Nombre:  " + bovinos.get(position).getbovino());
                           bovinoSeleccionadoP = bovinosP.get(position).getbovino();
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

        ArrayAdapter<CharSequence> adapterRaza = ArrayAdapter.createFromResource(this,
                R.array.raza, android.R.layout.simple_spinner_item);
        adapterRaza.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selRaza.setAdapter(adapterRaza);
        selRaza.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterGenero = ArrayAdapter.createFromResource(this,
                R.array.genero, android.R.layout.simple_spinner_item);
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selGenero.setAdapter(adapterGenero);
        selGenero.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        razaSpin = selRaza.getItemAtPosition(position).toString();
        sexoSpin = selGenero.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void goBack(){
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
        String raza = razaSpin;//selRaza spinner
        String madre = bovinoSeleccionadoM;//selMadre spinner
        String padre = bovinoSeleccionadoP;//selPadre spinner
        Boolean sexo = getSexo();
        String fecha = textDate.getText().toString();
        String pesoNacimiento = textAlNacer.getText().toString();
        String pesonDestete = textAlDestete.getText().toString();
        String pesoMeses = text12Meses.getText().toString();

        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(id) && TextUtils.isEmpty(fecha) && TextUtils.isEmpty(pesoNacimiento)
                && TextUtils.isEmpty(pesonDestete) && TextUtils.isEmpty(pesoMeses)) { // && TextUtils.isEmpty(raza) && TextUtils.isEmpty(madre) && TextUtils.isEmpty(padre)

            Toast.makeText(NewBovinoActivity.this, "Ingrese todos los datos.", Toast.LENGTH_SHORT).show();
        } else {
            addDatatoFirebase(name, id, raza, madre, padre, fecha, pesoNacimiento, pesonDestete, pesoMeses, sexo);
        }
    }

    private void addDatatoFirebase(String p_name, String p_id, String p_raza, String p_madre, String p_padre,
                                   String p_fecha, String p_Nacimiento, String p_Destete, String p_Meses, Boolean sexo) {

        CollectionReference dbBovino = db.collection("bovino");

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
                bovino nuevoBovino = new bovino(p_name, p_id, p_raza, p_padre, p_madre, p_fecha, p_Nacimiento,
                p_Destete, p_Meses, idFinca, sexo);

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
                        Boolean sexo = qs.getBoolean("sexo");
                        if(sexo) bovinosTorosHash.put(name,id);
                        else bovinosVacasHash.put(name,id);
                    }
                }
                //getInfoVaca();
            }
        });
        spinnerBovinoMadre();
        spinnerBovinoPadre();
    }

    private Boolean getSexo(){
        if(sexoSpin.equals("Macho")) return true;
        else return false;
    }
}