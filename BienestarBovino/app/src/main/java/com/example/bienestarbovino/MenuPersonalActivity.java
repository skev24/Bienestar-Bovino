package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuPersonalActivity extends AppCompatActivity {

    private ImageButton btnPersonal, btnTarea;
    private Button btnReturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_personal);

        btnPersonal = findViewById(R.id.btnPersonal2);
        btnTarea = findViewById(R.id.btnTareas2);
        btnReturn = findViewById(R.id.btnRegPersonal);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        btnTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTarea(view);
            }
        });

        btnPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPersonal(view);
            }
        });

    }

    public void openTarea(View view){
        Intent intent = new Intent(MenuPersonalActivity.this, TaskActivity.class);
        startActivity(intent);
    }

    public void openPersonal(View view){
        Intent intent = new Intent(MenuPersonalActivity.this, ListPersonalActivity.class);
        startActivity(intent);
    }

    public void goBack(){
        Intent intent = new Intent(MenuPersonalActivity.this, TaskActivity.class);
        startActivity(intent);
    }
}
