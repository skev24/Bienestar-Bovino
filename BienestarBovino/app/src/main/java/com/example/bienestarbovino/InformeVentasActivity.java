

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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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

public class InformeVentasActivity extends AppCompatActivity implements Funciones, AdapterView.OnItemSelectedListener {

    private Spinner seleccionarInfoVenta;
    private Button btnRegresar;
    private String idFincaGlobal = "";
    private HashMap<String, String> bovinosHash;
    private String bovinoSeleccionado = "";

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private List<venta> bovinos = new ArrayList<>();
    private List<String> ventasBovinos = new ArrayList<>();


    //Variables necesarias para la grafica
    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

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
        bovinosHash = new HashMap<>();

        cargarDatos();

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });


    }
    public void cargarSpinners(){

        ArrayAdapter<CharSequence> adapterGenero = ArrayAdapter.createFromResource(this,
                R.array.VentaEstadisticas, android.R.layout.simple_spinner_item);
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seleccionarInfoVenta.setAdapter(adapterGenero);
        seleccionarInfoVenta.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            bovinoSeleccionado = parent.getItemAtPosition(position).toString();
            // creating a new array list
            //DATOS PARA LA GRAFICA

            // initializing variable for bar chart.
            barChart = findViewById(R.id.idBarChart);

            // calling method to get bar entries.
            //getBarEntries();
            barEntriesArrayList = new ArrayList<>();

            // adding new entry to our array list with bar
            // entry and passing x and y axis value to it.
            Log.e("HERE",""+position);
            barEntriesArrayList.add(new BarEntry(1F, position+4));
            barEntriesArrayList.add(new BarEntry(2f, position+3));
            barEntriesArrayList.add(new BarEntry(3f, position+2));
            barEntriesArrayList.add(new BarEntry(4f, position+1));


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


        }else if(position == 1){
            bovinoSeleccionado = parent.getItemAtPosition(position).toString();
            // creating a new array list
            //DATOS PARA LA GRAFICA

            // initializing variable for bar chart.
            barChart = findViewById(R.id.idBarChart);

            // calling method to get bar entries.
            //getBarEntries();
            barEntriesArrayList = new ArrayList<>();

            // adding new entry to our array list with bar
            // entry and passing x and y axis value to it.
            Log.e("HERE",""+position);
            barEntriesArrayList.add(new BarEntry(1F, 44));
            barEntriesArrayList.add(new BarEntry(2f, 43));
            barEntriesArrayList.add(new BarEntry(3f, 2));
            barEntriesArrayList.add(new BarEntry(4f, 1));


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



        }else if(position == 2){
            bovinoSeleccionado = parent.getItemAtPosition(position).toString();
            // creating a new array list
            //DATOS PARA LA GRAFICA

            // initializing variable for bar chart.
            barChart = findViewById(R.id.idBarChart);

            // calling method to get bar entries.
            //getBarEntries();
            barEntriesArrayList = new ArrayList<>();

            // adding new entry to our array list with bar
            // entry and passing x and y axis value to it.
            Log.e("HERE",""+position);
            barEntriesArrayList.add(new BarEntry(1F, 1));
            barEntriesArrayList.add(new BarEntry(2f, 2));
            barEntriesArrayList.add(new BarEntry(3f, 3));
            barEntriesArrayList.add(new BarEntry(4f, 1));


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
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        cargarSpinners();
    }


    public void goBack(){
        Intent intent = new Intent(InformeVentasActivity.this, MenuInformesActivity.class);
        startActivity(intent);
    }

}