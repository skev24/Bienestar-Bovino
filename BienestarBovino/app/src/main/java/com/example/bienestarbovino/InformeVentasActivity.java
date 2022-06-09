package com.example.bienestarbovino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import control.Funciones;
import model.venta;


//Funciones Para Graficas
import android.graphics.Color;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
//Fin

public class InformeVentasActivity extends AppCompatActivity implements Funciones {

    private Spinner seleccionarInfoVenta;
    private Button btnRegresar;
    private String idFincaGlobal = "";
    private HashMap<String, String> bovinosHash;
    private String bovinoSeleccionado = "";

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private List<venta> bovinos = new ArrayList<>();
    private List<venta> ventasBovinos = new ArrayList<>();


    //Variables necesarias para la grafica
    // variable for our bar chart
    private BarChart barChart;

    // variable for our bar data.
    private BarData barData;

    // variable for our bar data set.
    private BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;
    //Fin Variables para la grafica

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadistica_ventas);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        seleccionarInfoVenta = findViewById(R.id.spinnerEstadisticaVenta);
        btnRegresar = findViewById(R.id.btnRegVen);


        //DATOS PARA LA GRAFICA

        // initializing variable for bar chart.
        barChart = findViewById(R.id.idBarChart);

        // calling method to get bar entries.
        getBarEntries();

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Bienestar Bovino");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

        //FIN DATOS PARA LA GRAFICA

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        cargarSpinners();
    }

    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, 4));
        barEntriesArrayList.add(new BarEntry(2f, 6));
        barEntriesArrayList.add(new BarEntry(3f, 8));
        barEntriesArrayList.add(new BarEntry(4f, 2));
        barEntriesArrayList.add(new BarEntry(5f, 4));
        barEntriesArrayList.add(new BarEntry(6f, 1));
    }

    public void cargarSpinners(){

        db.collection("venta").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String name = qs.getString("name");
                    String raza = qs.getString("raza");
                    String id = qs.getString("id");
                    //if(qs.getString("fincaId").equals(idFincaGlobal) && qs.getBoolean("activoEnFinca").equals(Boolean.TRUE))
                        bovinos.add(new venta(name,id,raza));
                }
            }
        });

        ArrayAdapter<CharSequence> adapterVacas = ArrayAdapter.createFromResource(this,
                R.array.VentaEstadisticas, android.R.layout.simple_spinner_item);
        adapterVacas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seleccionarInfoVenta.setAdapter(adapterVacas);
        seleccionarInfoVenta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bovinoSeleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }
        );
    }


    public void goBack(){
        Intent intent = new Intent(InformeVentasActivity.this, MenuInformesActivity.class);
        startActivity(intent);
    }
}