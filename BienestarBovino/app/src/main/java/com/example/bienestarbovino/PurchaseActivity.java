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

import control.Funciones;
import model.bovino;
import model.compra;

public class PurchaseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Funciones {

    private EditText textNameBovino, textIdBovino, textCosto, textDate;
    private Spinner selRaza, sexoSpinner;
    private Button buttonCompraRegresar;
    private Button buttonGuardarCompra;
    String idNEW = new String();
    String raz ="";
    private String sexoSpin = "";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference dbBovino = db.collection("bovino");
    CollectionReference dbCompraBovi = db.collection("compraxbovino");

    //private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.compra);

        selRaza = findViewById(R.id.spinnerBovinoCompra);
        sexoSpinner = findViewById(R.id.spinnerSexoCompra);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //datos de interfaz
        textNameBovino = findViewById(R.id.editTextId);
        textCosto =findViewById(R.id.editTextPrice);
        textIdBovino = findViewById(R.id.editTextNombre);
        textDate = findViewById(R.id.TextViewDatePurchase);

        buttonCompraRegresar = findViewById(R.id.btnCompraRegresar);
        buttonGuardarCompra = findViewById(R.id.buttonCompraRegister);

        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar(view);
            }
        });

        buttonCompraRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        buttonGuardarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compararBovino(view);
            }
        });

        cargarSpinners();

    }

    public void cargarSpinners(){

        ArrayAdapter<CharSequence> adapterGenero = ArrayAdapter.createFromResource(this,
                R.array.genero, android.R.layout.simple_spinner_item);
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexoSpinner.setAdapter(adapterGenero);
        sexoSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.raza, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selRaza.setAdapter(adapter);
        selRaza.setOnItemSelectedListener(this);
    }


    @Override
    protected void onStart() {//Esta parte se activa solita y lee el ultimo Doc ID y lo retorna para saber el id del dato recientemente guardado
        super.onStart();
        dbBovino.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    DocumentSnapshot documentSnapshot = dc.getDocument();
                    String idNEW = documentSnapshot.getId();
                    Log.i("ID_Bovino", idNEW);
                }
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        raz = selRaza.getItemAtPosition(position).toString();
        sexoSpin = sexoSpinner.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void goBack(){
        Intent intent = new Intent(PurchaseActivity.this, InventoryActivity.class);
        startActivity(intent);
    }

    public void openCalendar(View view){
        Calendar calendar = Calendar.getInstance();
        int yearD = calendar.get(Calendar.YEAR);
        int monthD = calendar.get(Calendar.MONTH);
        int dayD = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(PurchaseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                textDate.setText(date);
            }
        }, yearD, monthD, dayD);
        dpd.show();
    }
    public void compararBovino(View view){
        String name = textNameBovino.getText().toString();
        String id = textIdBovino.getText().toString();
        String raza = raz;//Raza spinner
        String fecha = textDate.getText().toString();
        String precio = textCosto.getText().toString();
        Boolean genero = getSexo();
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(id) && TextUtils.isEmpty(fecha)) { // && TextUtils.isEmpty(raza) && TextUtils.isEmpty(madre) && TextUtils.isEmpty(padre)

            Toast.makeText(PurchaseActivity.this, "Ingrese todos los datos.", Toast.LENGTH_SHORT).show();
        } else {
            addDatatoFirebase(precio, name, id, raza, fecha, genero);
            buttonGuardarCompra.setEnabled(false);
            /**try {
             Thread.sleep( 20000);
             Log.i("Tiempo", "entro");
             } catch (InterruptedException e) {
             e.printStackTrace();
             }*/
            guardarCompra(precio, fecha, idNEW);
        }

    }

    private  void guardarCompra(String p_precio, String p_fecha, String id_NEW){
        int precioINT = Integer.valueOf(p_precio);

        Log.i("ID_Bovino", p_precio);
        Log.i("ID_Bovino", p_fecha);
        Log.i("ID_Bovino", id_NEW);


        compra comprarBovino = new compra(precioINT, p_fecha, id_NEW);
        dbCompraBovi.add(comprarBovino).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(PurchaseActivity.this, "Compra agregada.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PurchaseActivity.this, "Error al comprar bovino. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void agregarCompraBovino(String name, String p_id, String p_raza, String idFinca, String fecha, Boolean genero){
        //String idCompra ="";
        //int cont = 0;
        //final String[] idbovi = {new String("")};
        //Esto es un print en consola Log.i("TAG", "MENSAJE");

        bovino crearBovino = new bovino(p_id, name, p_raza, "N/A", "N/A", fecha, "0", "0", "0", idFinca, genero);

        dbBovino.add(crearBovino).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(PurchaseActivity.this, "Bovino agregado.", Toast.LENGTH_SHORT).show();
                goBack();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PurchaseActivity.this, "Error al agregar bovino. \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void addDatatoFirebase(String p_precio, String p_name, String p_id, String p_raza, String p_fecha, Boolean genero) {

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

                agregarCompraBovino(p_name, p_id, p_raza, idFinca, p_fecha, genero);
            }
        });
    }

    private Boolean getSexo(){
        if(sexoSpin.equals("Macho")) return true;
        else return false;
    }
}