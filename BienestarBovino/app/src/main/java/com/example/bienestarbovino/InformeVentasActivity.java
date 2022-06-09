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

    private List<String> toro = new ArrayList<>();
    private List<String> vaca = new ArrayList<>();

    private List<String> toroRaza = new ArrayList<>();
    private List<String> vacaRaza = new ArrayList<>();

    private List<String> toroRazaVendida = new ArrayList<>();
    private List<String> vacaRazaVendida = new ArrayList<>();

    private List<String> toroRazaVendidaCont = new ArrayList<>();
    private List<String> vacaRazaVendidaCont = new ArrayList<>();

    private List<String> toroVendido = new ArrayList<>();
    private List<Integer> toroVenta = new ArrayList<>();

    private List<String> vacaVendido = new ArrayList<>();
    private List<Integer> vacaVenta = new ArrayList<>();

    private HashMap<String, String> bovinosVacasHash, bovinosTorosHash;

    private int contVacas=0;
    private int contToro=0;


    //Variables necesarias para la grafica
    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList1;
    //Fin Variables para la grafica
    ArrayList barEntriesArrayList2;
    ArrayList barEntriesArrayList3;

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
        contarMonto();
        cargarSpinners();


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

            barChart = findViewById(R.id.idBarChart);

            BarDataSet data1 = new BarDataSet(dataValues1(toroVendido.size()), "Macho");
            BarDataSet data2 = new BarDataSet(dataValues2(vacaVendido.size()),"Hembra");

            data1.setColor(Color.GREEN);
            data2.setColor(Color.RED);

            BarData barData = new BarData();
            barData.addDataSet(data1);
            barData.addDataSet(data2);

            barData.setValueTextSize(16f);

            barChart.setData(barData);
            barChart.invalidate();

            //FIN DATOS PARA LA GRAFICA
        }else if(position == 1){

            barChart = findViewById(R.id.idBarChart);

            BarDataSet data1 = new BarDataSet(dataValues1(toroVenta.get(toroVenta.size()-1)), "Macho");
            BarDataSet data2 = new BarDataSet(dataValues2(vacaVenta.get(vacaVenta.size()-1)),"Hembra");

            data1.setColor(Color.GREEN);
            data2.setColor(Color.RED);

            BarData barData = new BarData();
            barData.addDataSet(data1);
            barData.addDataSet(data2);

            barData.setValueTextSize(16f);

            barChart.setData(barData);
            barChart.invalidate();

        }else if(position == 2){

            barChart = findViewById(R.id.idBarChart);

            BarDataSet data1 = new BarDataSet(dataValues1(toroRazaVendidaCont.size()+1), "Holstein");
            BarDataSet data2 = new BarDataSet(dataValues2(vacaRazaVendidaCont.size()-toroRazaVendidaCont.size()),"SALES");

            data1.setColor(Color.GREEN);
            data2.setColor(Color.RED);

            BarData barData = new BarData();
            barData.addDataSet(data1);
            barData.addDataSet(data2);

            barData.setValueTextSize(16f);

            barChart.setData(barData);
            barChart.invalidate();

        }

    }

    private ArrayList<BarEntry> dataValues1(int val){
        ArrayList<BarEntry> dataVals = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.

        dataVals.add(new BarEntry(0F, val));
        return dataVals;
    }
    private ArrayList<BarEntry> dataValues2(int val){
        ArrayList<BarEntry> dataVals = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.

        dataVals.add(new BarEntry(1F, val));
        return dataVals;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void contarMonto(){
        int valor1 = 0;
        int valor2 = 0;
        for(int i=0;i<vacaVenta.size();i++){
            valor1 += vacaVenta.get(i);
        }
        for(int i=0;i<toroVenta.size();i++){
            valor2 += toroVenta.get(i);
        }
        vacaVenta.add(valor1);
        toroVenta.add(valor2);
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
                int i =0;
                int j=0;
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String finca = qs.getString("fincaId");

                    if(finca.equals(idFinca)){
                        String name = qs.getString("name");
                        String raza = qs.getString("raza");
                        Boolean sexo = qs.getBoolean("sexo");
                        if(sexo) {toro.add(name); toroRaza.add(raza);}
                        else {vaca.add(name); vacaRaza.add(raza);}
                    }
                }
            }
        });
        contadorVendidosToro();
    }



    public void contadorVendidosToro(){

        db.collection("venta").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            int i =0;
            int j =0;
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String name = qs.getString("bovino");
                    String monto = qs.getString("monto");
                    if(toro.contains(name)){
                        toroVendido.add(name);
                        toroVenta.add(Integer.parseInt(monto));
                        toroRazaVendidaCont.add(toroRaza.get(i++));
                    }
                    else if(vaca.contains(name)){
                        vacaVendido.add(name);
                        vacaVenta.add(Integer.parseInt(monto));
                        vacaRazaVendidaCont.add(vacaRaza.get(j++));

                    }

                }
            }


        });
    }


    public void goBack(){
        Intent intent = new Intent(InformeVentasActivity.this, MenuInformesActivity.class);
        startActivity(intent);
    }

}