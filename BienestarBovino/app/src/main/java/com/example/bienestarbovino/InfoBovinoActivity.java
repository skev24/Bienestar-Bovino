package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InfoBovinoActivity extends AppCompatActivity {

    private Button backToInventory;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_bovino);

        backToInventory = findViewById(R.id.btnInfoRegresar);
        name = findViewById(R.id.textViewInfoBovinoName);

        backToInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInventory();
            }
        });
    }

    public void openInventory(){
        Intent intent = new Intent(InfoBovinoActivity.this, InventoryActivity.class);
        startActivity(intent);
    }

    public void setName(String name){
        this.name.setText(name);
    }
}