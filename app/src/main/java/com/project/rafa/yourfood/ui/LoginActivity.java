package com.project.rafa.yourfood.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.rafa.yourfood.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText edUser, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

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
}
