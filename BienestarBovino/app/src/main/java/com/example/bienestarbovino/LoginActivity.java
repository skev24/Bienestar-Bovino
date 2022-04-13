package com.example.bienestarbovino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
//    private DatabaseReference db;
    private EditText email;
    private EditText password;

    private Button buttonLogin;
    private TextView textNewUser;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        email = findViewById(R.id.editTextEmailLogin);
        password = findViewById(R.id.editTextPasswordLogin);

        buttonLogin = findViewById(R.id.buttonAccept);
        textNewUser = findViewById(R.id.textViewNewUser);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(view);
            }
        });

        textNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });
    }

    public void openRegisterActivity(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser); método para comprobar si ya está con una sesión iniciada
    }

    public void loginUser(View view){
        //Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Sesion iniciada.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            escogerNuevaVentana();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Contraseña o correo incorrecto.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    public void escogerNuevaVentana(){
        Intent intent = new Intent(LoginActivity.this, FincaActivity.class);
        startActivity(intent);
//        db.child("Fincas").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int state = 0;
//                if (snapshot.exists()){
//                    for(DataSnapshot ds: snapshot.getChildren()){
//                        String user = ds.child("user").getValue().toString();
//                        if(user.equals(mAuth.getCurrentUser().getUid())){
//                            state = 1;
//                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
//                            startActivity(intent);
//                            break;
//                        }
//                    }
//                    if(state == 0){
//                        Intent intent = new Intent(LoginActivity.this, FincaActivity.class);
//                        startActivity(intent);
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }
}