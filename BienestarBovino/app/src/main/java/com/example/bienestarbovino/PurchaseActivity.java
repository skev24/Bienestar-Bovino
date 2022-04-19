package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class PurchaseActivity extends AppCompatActivity {

    private TextView textDate;
    private Button buttonOpenCalendar;
    private Button buttonCompraRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.compra);
        textDate = findViewById(R.id.TextViewDatePurchase);
        buttonOpenCalendar = findViewById(R.id.buttonCalendar);
        buttonCompraRegresar = findViewById(R.id.btnCompraRegresar);

        buttonOpenCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar(view);
            }
        });

        buttonCompraRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCompraRegresarActivity();
            }
        });
    }

    public void openCalendar(View view){
        Calendar calendar = Calendar.getInstance();
        int yearD = calendar.get(Calendar.YEAR);
        int monthD = calendar.get(Calendar.MONTH);
        int dayD = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(PurchaseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                textDate.setText(date);
            }
        }, yearD, monthD, dayD);
        dpd.show();
    }

    public void openCompraRegresarActivity(){
        Intent intent = new Intent(PurchaseActivity.this, InventoryActivity.class);
        startActivity(intent);
    }
}