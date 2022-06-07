package com.example.bienestarbovino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadistica_ventas);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        seleccionarInfoVenta = findViewById(R.id.spinnerEstadisticaVenta);
        btnRegresar = findViewById(R.id.btnRegVen);


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

        //dieta
    }



    /*
    public void spinnerBovino(){



        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String name = qs.getString("name");
                    String raza = qs.getString("raza");
                    String id = qs.getString("id");

                    bovinos.add(new venta(name,id,raza));


                    //Toast.makeText(SaleActivity.this, id, Toast.LENGTH_SHORT).show();
                    if(qs.getString("fincaId").equals(idFincaGlobal) && qs.getBoolean("activoEnFinca").equals(Boolean.TRUE))
                        bovinos.add(new venta(name,id,raza));

                }

                ArrayAdapter<venta> arrayAdapter = new ArrayAdapter<>(SaleActivity.this, android.R.layout.simple_dropdown_item_1line, bovinos);
                seleccionarInfoVenta.setAdapter(arrayAdapter);
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
    */


    public void goBack(){
        Intent intent = new Intent(InformeVentasActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}