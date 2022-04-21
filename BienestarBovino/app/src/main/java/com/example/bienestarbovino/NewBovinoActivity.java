package com.example.bienestarbovino;

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

public class NewBovinoActivity extends AppCompatActivity {

    private EditText textNameBovino, textIdBovino, textAlNacer, textAlDestete, text12Meses;
    private TextView textDate;
    private Button buttonOpenCalendar;
    private Button buttonAddBovino, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbovino);
//        textNameBovino = findViewById(R.id.editTextaddbovino);
//        textIdBovino = findViewById(R.id.editTextaddbovino4);
//        textAlNacer = findViewById(R.id.editTextaddbovino2);
//        textAlDestete = findViewById(R.id.editTextaddbovino1);
//        text12Meses = findViewById(R.id.editTextaddbovino3);
        textDate = findViewById(R.id.textViewaddbovino9);
        buttonOpenCalendar = findViewById(R.id.buttonaddbovino);
        //buttonAddBovino = findViewById(R.id.buttonaddbovino5);
        buttonBack = findViewById(R.id.buttonaddbovino4);

        buttonOpenCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar(view);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentaRegresarActivity();
            }
        });
    }

    public void openVentaRegresarActivity(){
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
}