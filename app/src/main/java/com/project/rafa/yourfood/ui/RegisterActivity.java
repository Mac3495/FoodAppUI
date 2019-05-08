package com.project.rafa.yourfood.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.data.FoodUser;

public class RegisterActivity extends AppCompatActivity {

    EditText edUser, edMail, edPassword, edDescription;
    Button btnRegister;

    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edUser = findViewById(R.id.ed_user_register);
        edPassword = findViewById(R.id.ed_password_registerr);
        edMail = findViewById(R.id.ed_mail_register);
        edDescription = findViewById(R.id.ed_description_register);
        btnRegister = findViewById(R.id.btn_register);
        mProgressBar = findViewById(R.id.progress_bar);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    void register(){
        final String user = edUser.getText().toString().trim();
        final String password = edPassword.getText().toString().trim();
        final String mail = edMail.getText().toString().trim();
        final String description = edDescription.getText().toString().trim();


        mProgressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(mail,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

                            mProgressBar.setVisibility(View.GONE);
                            FoodUser foodUser = new FoodUser(user, password, mail, description, id);

                            FirebaseFirestore.getInstance().collection("user").document(id).set(foodUser);

                            Intent welcome = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(welcome);

                        }

                        else {
                            Toast.makeText(getApplicationContext(),"Task no successful", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
