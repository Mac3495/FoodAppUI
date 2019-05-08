package com.project.rafa.yourfood.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.rafa.yourfood.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText edUser, edPassword;
    private FirebaseAuth mauth;
    FirebaseAuth.AuthStateListener auth_listener;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        edUser = findViewById(R.id.ed_user);
        edPassword = findViewById(R.id.ed_password);
        mProgressBar = findViewById(R.id.progress_bar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        inicialize();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    void loginUser(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    void registerUser(){
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

    private void inicialize(){
        //AuthListener detecta los cambios que hay en la sesion
        mauth = FirebaseAuth.getInstance(); //FirebaseAuth para trabajar con sesiones de firebase
        auth_listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mauth.getCurrentUser();  //Este objeto toma los datos del usuario que esta logueado
                if(user != null){
                    Toast.makeText(LoginActivity.this, "Logueado", Toast.LENGTH_SHORT).show();
                    Intent welcome = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(welcome);
                }else{
                    Toast.makeText(LoginActivity.this, "No logeado", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void login(){
        final String user = edUser.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "Debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);
        mauth.signInWithEmailAndPassword(user,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser usera = FirebaseAuth.getInstance().getCurrentUser();
                    if(usera!=null){
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, ":)", Toast.LENGTH_SHORT).show();
                        Intent welcome = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(welcome);
                    }
                    //Toast.makeText(getApplicationContext(),"Bienvenid@ "+ email.getText(),Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Datos Incorrecto",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(auth_listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(auth_listener != null){
            mauth.removeAuthStateListener(auth_listener);
        }
    }
}
