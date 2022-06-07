package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import model.venta;

public class SaleActivity extends AppCompatActivity implements Funciones {

    private TextView textDate;
    private TextView textID;
    private TextView textRaza;
    private TextView textName;
    private Button buttonVentaRegresar, buttonGuardarVenta;
    private EditText editFecha, editMonto;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Spinner bovinoName;
    private HashMap<String, String> bovinosHash;

    private List<venta> bovinos = new ArrayList<>();
    private String bovinoSeleccionado = "";
    private String idFincaGlobal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.venta);

        bovinoName = findViewById(R.id.spinnerVenta);
        textID = findViewById(R.id.textView4);
        textName = findViewById(R.id.textViewNombreVenta);
        textRaza = findViewById(R.id.textViewRazaVenta);


        editFecha = findViewById(R.id.editFechaVenta);
        editMonto = findViewById(R.id.editMontoVenta);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        buttonVentaRegresar = findViewById(R.id.btnVentaRegresar);
        buttonGuardarVenta = findViewById(R.id.buttonVentaRegister);
        bovinosHash = new HashMap<>();
//        spinnerBovino();

        buttonVentaRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
        buttonGuardarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVenta(view);
            }
        });

        editFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar(view);
            }
        });

        cargarDatos();
    }

    public void spinnerBovino(){

        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                        String name = qs.getString("name");
                        String raza = qs.getString("raza");
                        String id = qs.getString("id");
                        //Toast.makeText(SaleActivity.this, id, Toast.LENGTH_SHORT).show();
                        if(qs.getString("fincaId").equals(idFincaGlobal) && qs.getBoolean("activoEnFinca").equals(Boolean.TRUE))
                            bovinos.add(new venta(name,id,raza));
                }
                ArrayAdapter<venta> arrayAdapter = new ArrayAdapter<>(SaleActivity.this, android.R.layout.simple_dropdown_item_1line, bovinos);
                bovinoName.setAdapter(arrayAdapter);
                bovinoName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //bovinoSeleccionado = parent.getItemAtPosition(position).toString();
                        textID.setText(bovinos.get(position).getmonto());
                        textName.setText(bovinos.get(position).getbovino());
                        bovinoSeleccionado = bovinos.get(position).getbovino();
                        textRaza.setText(bovinos.get(position).getfecha());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }

                );

            }
        });
    }

    public void openCalendar(View view){
        Calendar calendar = Calendar.getInstance();
        int yearD = calendar.get(Calendar.YEAR);
        int monthD = calendar.get(Calendar.MONTH);
        int dayD = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(SaleActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                textDate.setText(date);
            }
        }, yearD, monthD, dayD);
        dpd.show();
    }

    public void goBack(){
        Intent intent = new Intent(SaleActivity.this, InventoryActivity.class);
        startActivity(intent);
    }

    //addVenta registra la venta de un bovino y valida lo ingresado.
    //Recibe la vista de vacunacion.xml
    public void addVenta(View view){
        String bovino = bovinoSeleccionado;//selBovino spinner
        String monto = editMonto.getText().toString();
        String fecha = editFecha.getText().toString();

        if (TextUtils.isEmpty(bovino) && TextUtils.isEmpty(monto) && TextUtils.isEmpty(fecha)) {

            Toast.makeText(SaleActivity.this, "Ingrese todos los datos.", Toast.LENGTH_SHORT).show();
        } else {
            addDatatoFirebase(bovino, monto, fecha, view);
        }
    }

    //addDataToFirebase agrega a la base de datos la venta
    private void addDatatoFirebase(String p_bovino, String p_monto, String p_fecha, View view) {

        CollectionReference dbVacuna = db.collection("venta");
        venta nuevaVenta = new venta(p_bovino, p_monto, p_fecha);

        dbVacuna.add(nuevaVenta).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                db.collection("bovino").document(bovinosHash.get(bovinoSeleccionado)).update("activoEnFinca",false);
                Toast.makeText(SaleActivity.this, "Registro de bovino vendido.", Toast.LENGTH_SHORT).show();
                goBack();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SaleActivity.this, "Error al agregar registro de venta. \n" + e, Toast.LENGTH_SHORT).show();
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