package com.example.bienestarbovino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import model.Register;

public class RegisterActivity extends AppCompatActivity {

    private EditText userRegister, emailRegister, passwordRegister, confirmPasswordRegister;
    private Button registerNew;
    private TextView iniciarSesion;
    private String userReg, emailReg, passwordReg, confirmPasswordReg;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        userRegister = findViewById(R.id.editTextUserRegister);
        emailRegister = findViewById(R.id.editTextEmailRegister);
        passwordRegister = findViewById(R.id.editTextTextPasswordRegister);
        confirmPasswordRegister = findViewById(R.id.editTextTextConfirmPasswordRegister);
        registerNew = findViewById(R.id.buttonRegisterNew);
        iniciarSesion = findViewById(R.id.textViewIniciarSesion);

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

        registerNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(v);
//                userReg = userRegister.getText().toString();
//                emailReg = emailRegister.getText().toString();
//                passwordReg = passwordRegister.getText().toString();
//                confirmPasswordReg = confirmPasswordRegister.getText().toString();
//
//                if (TextUtils.isEmpty(userReg)) {
//                    userRegister.setError("Ingrese un usuario valido");
//                    Toast.makeText(RegisterActivity.this, "ERROR \n", Toast.LENGTH_SHORT).show();
//                } else if (TextUtils.isEmpty(emailReg)) {
//                    emailRegister.setError("Ingrese un correo valido");
//                    Toast.makeText(RegisterActivity.this, "ERROR \n", Toast.LENGTH_SHORT).show();
//                } else if (TextUtils.isEmpty(passwordReg)) {
//                    passwordRegister.setError("Ingrese un contraseña valida");
//                    Toast.makeText(RegisterActivity.this, "ERROR \n", Toast.LENGTH_SHORT).show();
//                } else if (TextUtils.isEmpty(confirmPasswordReg)) {
//                    confirmPasswordRegister.setError("Ingrese un contraseña valida");
//                    Toast.makeText(RegisterActivity.this, "ERROR \n", Toast.LENGTH_SHORT).show();
//                } else if (!(passwordReg.equals(confirmPasswordReg))){
//                    Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden. \n", Toast.LENGTH_SHORT).show();
//                }else {
//                    addDataToFirestore(userReg, emailReg, passwordReg);
//                    openLoginActivity();
//                }
            }
        });
    }

    public void openLoginActivity(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void addDataToFirestore(String userReg, String emailReg, String passwordReg) {

        CollectionReference dbCourses = db.collection("usuario");

        Register nuevoUsuario = new Register(userReg, emailReg, passwordReg);

        dbCourses.add(nuevoUsuario).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(RegisterActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error al crear usuario \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registerUser(View view) {
        if (passwordRegister.getText().toString().equals(confirmPasswordRegister.getText().toString())) {
            mAuth.createUserWithEmailAndPassword(emailRegister.getText().toString(), passwordRegister.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(getApplicationContext(), "Usuario creado.",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(i);
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
        }
    }


}