package com.project.rafa.yourfood.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.data.FoodUser;

import org.w3c.dom.Text;

public class EditUserActivity extends AppCompatActivity {

    TextView usernameTextView;
    Button updateButton;
    EditText currentPassword;
    EditText newPassword;
    EditText description;

    FoodUser foodUser;

    String email;
    final FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        usernameTextView = (TextView) findViewById(R.id.tv_edit_user);
        updateButton = (Button) findViewById(R.id.btn_edit_data);
        currentPassword = (EditText) findViewById(R.id.et_current_password);
        newPassword = (EditText) findViewById(R.id.et_edit_password);
        description = (EditText) findViewById(R.id.et_edit_description);

        setData();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();

                updateDescription();

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);

                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("user", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    void setData(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        database.collection("user").whereEqualTo("userId", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc : task.getResult()){
                        foodUser = doc.toObject(FoodUser.class);
                        usernameTextView.setText("Usuario: " + foodUser.getUser());
                        description.setText(foodUser.getDescription());
                        email = foodUser.getMail();
                    }
                }
            }
        });
    }

    void updateDescription(){
        foodUser.setDescription(description.getText().toString());
        database.collection("user").document(foodUser.getUserId()).set(foodUser);
    }

    void updatePassword(){
        final String newPasswordText = newPassword.getText().toString();

        // Si es que escribimos algo para actualizar la contraseña
        if (!newPasswordText.equals("") && !currentPassword.getText().toString().equals("")){
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            AuthCredential credential = EmailAuthProvider
                    .getCredential(email, currentPassword.getText().toString());

            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(newPasswordText).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            foodUser.setPassword(newPasswordText);
                                            database.collection("user").document(foodUser.getUserId()).set(foodUser);
                                            Toast.makeText(getApplicationContext(),"Contraseña actualizada", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(),"Error al actualizar", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(),"Error al autenticar", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }
}
