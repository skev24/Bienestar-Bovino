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

public class InventoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, Funciones {

    private Button btnVentaClass;
    private Button btnCompraClass;
    private Button btnRegresarClass;

    private ListView listViewBovino;
    private List<String> listBovino = new ArrayList<>();
    private List<String> listIdBovino = new ArrayList<>();
    private ArrayAdapter<String> listAdapter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventario);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnVentaClass = findViewById(R.id.btnVentaInventory);
        btnCompraClass = findViewById(R.id.btnCompraInventory);
        btnRegresarClass = findViewById(R.id.btnRegresarInventory);
        listViewBovino = findViewById(R.id.listViewBovinos);
        listViewBovino.setOnItemClickListener(this);

        btnVentaClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVentaActivity();
            }
        });

        btnCompraClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCompraActivity();
            }
        });

        btnRegresarClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        cargarDatos();
    }

    public void openVentaActivity(){
        Intent intent = new Intent(InventoryActivity.this, SaleActivity.class);
        startActivity(intent);
    }

    public void openCompraActivity(){
        Intent intent = new Intent(InventoryActivity.this, PurchaseActivity.class);
        startActivity(intent);
    }

    public void goBack(){
        Intent intent = new Intent(InventoryActivity.this, MenuActivity.class);
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
        db.collection("bovino").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot qs: queryDocumentSnapshots.getDocuments()){
                    String finca = qs.getString("fincaId");
                    if(finca.equals(idFinca) && qs.getBoolean("activoEnFinca").equals(Boolean.TRUE)){
                        String name = qs.getString("name");
                        String id = qs.getId();
                        listBovino.add(name);
                        listIdBovino.add(id);
                    }
                }
                listAdapter = new ArrayAdapter<>(InventoryActivity.this, android.R.layout.simple_list_item_1, listBovino);
                listViewBovino.setAdapter(listAdapter);

            }
        });
        //Toast.makeText(InventoryActivity.this, idFinca, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        //Intent intent = new Intent(InventoryActivity.this, InfoBovinoActivity.class);
        //intent.putExtra("name", "vaca");
        //startActivity(intent);
        Toast.makeText(InventoryActivity.this, "Id: "+listIdBovino.get(position), Toast.LENGTH_SHORT).show();
    }
}