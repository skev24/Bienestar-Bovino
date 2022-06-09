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

public class ListPersonalActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, Funciones {

    private Button btnAgregarPersonal;
    private Button btnRegresar;

    private ListView listViewPersonal;
    private List<String> listNombrePersonal = new ArrayList<>();
    private List<String> listTipoPersonal = new ArrayList<>();
    private ArrayAdapter<String> listAdapter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_personal);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnAgregarPersonal = findViewById(R.id.btnAgregarListaPersonal);
        btnRegresar = findViewById(R.id.btnRegresarListaPersonal);
        listViewPersonal = findViewById(R.id.listViewPersonal);
        listViewPersonal.setOnItemClickListener(this);

        btnAgregarPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPersonalActivity();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        cargarDatos();
    }

    public void openPersonalActivity(){
        Intent intent = new Intent(ListPersonalActivity.this, PersonalActivity.class);
        startActivity(intent);
    }

    public void goBack(){
        Intent intent = new Intent(ListPersonalActivity.this, MenuPersonalActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Toast.makeText(ListPersonalActivity.this, "Tipo: "+listTipoPersonal.get(position), Toast.LENGTH_SHORT).show();
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
                getDataPersonal(idFinca);
            }
        });
    }

    public void getDataPersonal(String idFinca){
        db.collection("personal").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String finca = qs.getString("fincaId");
                    if(finca.equals(idFinca) && qs.getBoolean("activoEnFinca").equals(Boolean.TRUE)){
                        String name = qs.getString("nombre");
                        String lastname = qs.getString("apellido");
                        String tipo = qs.getString("tipo");
                        listNombrePersonal.add(name + " " + lastname);
                        listTipoPersonal.add(tipo);
                    }
                }
                listAdapter = new ArrayAdapter<>(ListPersonalActivity.this, android.R.layout.simple_list_item_1, listNombrePersonal);
                listViewPersonal.setAdapter(listAdapter);
            }
        });
    }
}