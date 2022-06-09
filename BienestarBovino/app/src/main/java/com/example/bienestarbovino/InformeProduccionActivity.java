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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import control.Funciones;

public class InformeProduccionActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, Funciones {

    private Button btnRegresar, btnGuardar;
    private Spinner vacasGenero, vacasDieta;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String generoSpin = "";
    private String dietaSpin = "";

    private BarChart barChart;
    private String[] bovinos = new String[]{"lalo","papito","nueva","Color", "lalo","papito","nueva","Color", "lalo","papito","nueva","Color", "lalo","papito","nueva","Color"};
    private int[] pesos = new int[]{100, 200, 300, 400, 100, 200, 300, 400, 100, 200, 300, 400, 100, 200, 300, 400};
    private int[] colores = new int[]{Color.BLACK};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_produccion);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        vacasDieta = findViewById(R.id.spinnerDietaInfoProduccion);
        vacasGenero = findViewById(R.id.spinnerSexoInfoProduccion);

        btnRegresar = findViewById(R.id.btnInfoProduccionRegresar);
        btnGuardar = findViewById(R.id.buttonInforPrudGenerar);

        barChart = (BarChart) findViewById(R.id.idLinearChart);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCharts();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        cargarSpinners();
    }

    public void cargarSpinners(){
        ArrayAdapter<CharSequence> adapterVacas = ArrayAdapter.createFromResource(this,
                R.array.genero, android.R.layout.simple_spinner_item);
        adapterVacas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vacasGenero.setAdapter(adapterVacas);
        vacasGenero.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterDieta = ArrayAdapter.createFromResource(this,
                R.array.Dieta, android.R.layout.simple_spinner_item);
        adapterDieta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vacasDieta.setAdapter(adapterDieta);
        vacasDieta.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        generoSpin = vacasGenero.getItemAtPosition(position).toString();
        dietaSpin = vacasDieta.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), tipoSpin, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void goBack(){
        Intent intent = new Intent(InformeProduccionActivity.this, MenuInformesActivity.class);
        startActivity(intent);
    }

    private Chart getSameChart(Chart chart, String description, int textColor, int backgroundColor, int animateX){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(backgroundColor);
        chart.animateX(animateX);
        //setLegend(chart);

        return chart;
    }

    private void setLegend(Chart chart){
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries = new ArrayList<>();
        for(int i = 0; i< bovinos.length; i++){
            LegendEntry entry = new LegendEntry();
            entry.formColor = colores[0];
            entry.label = bovinos[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }

    private ArrayList<BarEntry> getBarEntries(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i< pesos.length; i++){
            entries.add(new BarEntry(i, pesos[i]));
        }
        return entries;
    }

    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(bovinos));
        axis.setLabelRotationAngle(90);
    }

    private void axisLeft(YAxis axis){
        axis.setSpaceTop(50);
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
        dataSet.setValueTextColor(Color.WHITE);
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