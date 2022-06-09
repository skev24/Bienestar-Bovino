package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import control.Funciones;
import model.personal;

public class PersonalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Funciones{

    private EditText textId, textName, textLastname;
    private Spinner selTipoUsuario;
    private Button buttonAddPersonal, buttonBack;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String TipoSpin = "";

    private String idFincaGlobal = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_personal);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        textName = findViewById(R.id.entryNameNewPersonal);
        textId = findViewById(R.id.entryIdNewPersonal);
        textLastname = findViewById(R.id.entryLastnameNewPersonal);
        selTipoUsuario = findViewById(R.id.spinnerTypeNewPersonal);
        buttonAddPersonal = findViewById(R.id.buttonGuardarNewPersonal);
        buttonBack = findViewById(R.id.buttonRegresarNewPersonal);

        buttonAddPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPersonal(view);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        cargarSpinners();
    }

    public void cargarSpinners(){
        ArrayAdapter<CharSequence> adapterDieta = ArrayAdapter.createFromResource(this,
                R.array.tipoPersonal, android.R.layout.simple_spinner_item);
        adapterDieta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selTipoUsuario.setAdapter(adapterDieta);
        selTipoUsuario.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TipoSpin = selTipoUsuario.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), tipoSpin, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void goBack() {
        Intent intent = new Intent(PersonalActivity.this, ListPersonalActivity.class);
        startActivity(intent);
    }


    public void addNewPersonal(View view) {
        String name = textName.getText().toString();
        String id = textId.getText().toString();
        String lastname = textLastname.getText().toString();
        String tipoSpin = TipoSpin;

        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(id) && TextUtils.isEmpty(name) && TextUtils.isEmpty(lastname)
                && TextUtils.isEmpty(id)) {
            Toast.makeText(PersonalActivity.this, "Ingrese todos los datos.", Toast.LENGTH_SHORT).show();
        } else {
            addDatatoFirebase(name, id, lastname, tipoSpin);
        }
    }

    private void addDatatoFirebase(String p_name, String p_id, String p_lastname, String p_tipo) {

        CollectionReference dbPersonal = db.collection("personal");

        db.collection("finca").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String idFinca = "";
                for (DocumentSnapshot qs : queryDocumentSnapshots.getDocuments()) {
                    String user = qs.getString("user");
                    if (user.equals(mAuth.getCurrentUser().getUid())) {
                        idFinca = qs.getId();
                        break;
                    }
                }
                personal nuevoPersonal = new personal(p_name, p_id, p_lastname, p_tipo, idFinca);

                dbPersonal.add(nuevoPersonal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(PersonalActivity.this, "Personal agregado.", Toast.LENGTH_SHORT).show();
                        goBack();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PersonalActivity.this, "Error al agregar personal. \n" + e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}

