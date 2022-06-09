package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import control.Funciones;

public class ListTaskActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, Funciones {

    private Button btnAddTask;
    private Button btnReturn;

    private ListView listViewBovino;
    private List<String> listDescripcion = new ArrayList<>();
    private List<String> listFecha = new ArrayList<>();
    private ArrayAdapter<String> listAdapter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_tareas);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnAddTask = findViewById(R.id.btnAgregarTarea);
        btnReturn = findViewById(R.id.btnRegresarListaTareas);
        listViewBovino = findViewById(R.id.listViewTareas);
        listViewBovino.setOnItemClickListener(this);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openAddTaskActivity();
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        cargarDatos();
    }

    public void openAddTaskActivity(){
        Intent intent = new Intent(ListTaskActivity.this, TaskActivity.class);
        startActivity(intent);
    }

    public void goBack(){
        Intent intent = new Intent(ListTaskActivity.this, MenuPersonalActivity.class);
        startActivity(intent);
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
        db.collection("tarea").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String finca = qs.getString("idFinca");
                    if(finca.equals(idFinca)){
                        String descripcion = qs.getString("descripcion");
                        String encargado = qs.getString("encargadoTarea");
                        String fecha = qs.getString("fecha");
                        boolean estado = qs.getBoolean("concluida");
                        listDescripcion.add(descripcion + ".    Encargado: " + encargado);
                        listFecha.add(fecha);
                    }
                }
                listAdapter = new ArrayAdapter<>(ListTaskActivity.this, android.R.layout.simple_list_item_1, listDescripcion);
                listViewBovino.setAdapter(listAdapter);

            }
        });
        //Toast.makeText(InventoryActivity.this, idFinca, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Toast.makeText(ListTaskActivity.this, "Fecha: "+listFecha.get(position), Toast.LENGTH_SHORT).show();
    }
}