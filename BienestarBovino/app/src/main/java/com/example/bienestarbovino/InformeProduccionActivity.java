package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
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

public class InformeProduccionActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, Funciones {

    private Button btnRegresar, btnGuardar;
    private Spinner bovinoSpinner, vacasDieta;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String bovinoSpin = "";
    private String dietaSpin = "";
    private List<venta> bovinos = new ArrayList<>();
    private HashMap<String, String> bovinosHash;
    private String idFincaGlobal = "";

    private BarChart barChart;
    private ArrayList<String> fechasPesaje = new ArrayList<>();//"lalo","papito","nueva","Color", "lalo","papito","nueva","Color", "lalo","papito","nueva","Color", "lalo","papito","nueva","Color");
    private ArrayList<Integer> pesos = new ArrayList<>(); // = new int[]{100, 200, 300, 400, 100, 200, 300, 400, 100, 200, 300, 400, 100, 200, 300, 400};
    private int[] colores = new int[]{Color.BLACK};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_produccion);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        bovinosHash = new HashMap<>();

        bovinoSpinner = findViewById(R.id.spinnerBovinoInfoProduccion);
        vacasDieta = findViewById(R.id.spinnerDietaInfoProduccion);

        btnRegresar = findViewById(R.id.btnInfoProduccionRegresar);
        btnGuardar = findViewById(R.id.buttonInforPrudGenerar);

        barChart = (BarChart) findViewById(R.id.idLinearChart);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerDatos();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        cargarDatos();
        cargarSpinners();
    }

    public void spinnerBovino(){

        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String name = qs.getString("name");
                    String raza = qs.getString("raza");
                    String id = qs.getString("id");
                    if(qs.getString("fincaId").equals(idFincaGlobal) && qs.getBoolean("activoEnFinca").equals(Boolean.TRUE))
                        bovinos.add(new venta(name,id,raza));
                }
                ArrayAdapter<venta> arrayAdapter = new ArrayAdapter<>(InformeProduccionActivity.this, android.R.layout.simple_dropdown_item_1line, bovinos);
                bovinoSpinner.setAdapter(arrayAdapter);
                bovinoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                       @Override
                       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                           bovinoSpin = bovinos.get(position).getbovino();
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
        ArrayAdapter<CharSequence> adapterDieta = ArrayAdapter.createFromResource(this,
                R.array.Dieta, android.R.layout.simple_spinner_item);
        adapterDieta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vacasDieta.setAdapter(adapterDieta);
        vacasDieta.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dietaSpin = vacasDieta.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), tipoSpin, Toast.LENGTH_SHORT).show();
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
        spinnerBovino();
    }

    public void goBack(){
        Intent intent = new Intent(InformeProduccionActivity.this, MenuInformesActivity.class);
        startActivity(intent);
    }

    //generar grafico

    private void obtenerDatos(){
        String id = bovinosHash.get(bovinoSpin);
        //String dieta = dietaSpin;
        db.collection("produccionPeso").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    if(id.equals(qs.getString("idBovino")) && qs.getString("dieta").equals(dietaSpin)){
                        fechasPesaje.add(qs.getString("fecha"));
                        pesos.add(Integer.parseInt(qs.getString("peso")));
                    }
                }
            }
        });
        if(fechasPesaje.isEmpty() && pesos.isEmpty())
            Toast.makeText(InformeProduccionActivity.this, "No hay datos de pesaje.", Toast.LENGTH_SHORT).show();
        else{
            createCharts();
        }
        fechasPesaje.clear();
        pesos.clear();
    }


    private Chart getSameChart(Chart chart, String description, int textColor, int backgroundColor, int animateX){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(backgroundColor);
        chart.animateX(animateX);
        setLegend(chart);

        return chart;
    }

    private void setLegend(Chart chart){
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);

        ArrayList<LegendEntry> entries = new ArrayList<>();
        for(int i = 0; i< 1; i++){
            LegendEntry entry = new LegendEntry();
            entry.formColor = colores[0];
            entry.label = bovinoSpin;//fechasPesaje.get(i);//fechasPesaje[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }

    private ArrayList<BarEntry> getBarEntries(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i< pesos.size(); i++){
            entries.add(new BarEntry(i, pesos.get(i)));
        }
        return entries;
    }

    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(fechasPesaje));
        //axis.setLabelRotationAngle(90);
    }

    private void axisLeft(YAxis axis){
        axis.setSpaceTop(120);
        axis.setAxisMinimum(0);
    }

    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }

    public void createCharts(){
        barChart = (BarChart) getSameChart(barChart, "", Color.RED, Color.GRAY,3000);
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(true);
        barChart.setData(getBarData());
        barChart.invalidate();

        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colores);
        dataSet.setForm(Legend.LegendForm.SQUARE);
        //dataSet.setDrawValues(false);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(15);
        return dataSet;
    }

    private BarData getBarData(){
        BarDataSet barDataSet = (BarDataSet) getData(new BarDataSet(getBarEntries(), ""));
        //barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }

}