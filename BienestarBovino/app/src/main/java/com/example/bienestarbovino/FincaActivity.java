package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.finca;

public class FincaActivity extends AppCompatActivity {

    private Button btnMenu;
    private EditText nameFinca, tamFinca;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    model.finca newFinca;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finca);

        mAuth = FirebaseAuth.getInstance();

        nameFinca = findViewById(R.id.editTextTextFincaName);
        tamFinca = findViewById(R.id.editTextTextFincaSize);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("finca");

        newFinca = new finca();

        btnMenu = findViewById(R.id.buttonContinueFinca);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenu(view);
            }
        });
    }

    public void openMenu(View view){

        // getting text from our edittext fields.
        String nameFinca = name.getText().toString();
        String phone = employeePhoneEdt.getText().toString();
        String address = employeeAddressEdt.getText().toString();

        // below line is for checking weather the
        // edittext fields are empty or not.
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(address)) {
            // if the text fields are empty
            // then show the below message.
            Toast.makeText(MainActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
        } else {
            // else call the method to add
            // data to our database.
            addDatatoFirebase(name, phone, address);
        }
        Intent intent = new Intent(FincaActivity.this, MenuActivity.class);
        startActivity(intent);

        //Toast.makeText(getApplicationContext(), mAuth.getCurrentUser().getUid(),
          //      Toast.LENGTH_SHORT).show();
    }
}