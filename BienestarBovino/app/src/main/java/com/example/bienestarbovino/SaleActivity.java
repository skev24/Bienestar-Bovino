package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import model.venta;

public class SaleActivity extends AppCompatActivity {

    private TextView textDate;
    private Button buttonVentaRegresar, buttonGuardarVenta;
    private EditText editFecha, editMonto;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.venta);

        editFecha = findViewById(R.id.editFechaVenta);
        editMonto = findViewById(R.id.editMontoVenta);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        buttonVentaRegresar = findViewById(R.id.btnVentaRegresar);
        buttonGuardarVenta = findViewById(R.id.buttonVentaRegister);

        buttonVentaRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentaRegresarActivity(view);
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

    public void openVentaRegresarActivity(View view){
        Intent intent = new Intent(SaleActivity.this, InventoryActivity.class);
        startActivity(intent);
    }

    //addVenta registra la venta de un bovino y valida lo ingresado.
    //Recibe la vista de vacunacion.xml
    public void addVenta(View view){
        String bovino = "toro 1";//selBovino spinner
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
                Toast.makeText(SaleActivity.this, "Registro de bovino vendido.", Toast.LENGTH_SHORT).show();
                openVentaRegresarActivity(view);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SaleActivity.this, "Error al agregar registro de venta. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}