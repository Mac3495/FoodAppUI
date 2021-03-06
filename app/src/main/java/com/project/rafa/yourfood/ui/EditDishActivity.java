package com.project.rafa.yourfood.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.data.Food;
import com.project.rafa.yourfood.data.FoodUser;

public class EditDishActivity extends AppCompatActivity {

    String foodId;
    Food food;
    final FirebaseFirestore database = FirebaseFirestore.getInstance();

    TextView editName;
    TextView editPreparation;
    TextView editIngredients;
    TextView editPrice;

    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dish);

        Bundle bundle = getIntent().getExtras();
        foodId = bundle.getString("foodId");

        btnAdd = findViewById(R.id.btn_edit_add);
        editName = findViewById(R.id.et_edit_name);
        editPreparation = findViewById(R.id.et_edit_preparation);
        editIngredients = findViewById(R.id.et_edit_ingredients);
        editPrice = findViewById(R.id.et_edit_price);

        setData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    void setData(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        database.collection("food").whereEqualTo("foodId", foodId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc : task.getResult()){
                        food = doc.toObject(Food.class);
                        editName.setText(food.getName());
                        editPreparation.setText(food.getPreparation());
                        editIngredients.setText(food.getIngredients());
                        editPrice.setText(food.getPrice());
                    }
                }
            }
        });
    }

    void update(){
        food.setName(editName.getText().toString());
        food.setPreparation(editPreparation.getText().toString());
        food.setIngredients(editIngredients.getText().toString());
        food.setPrice(editPrice.getText().toString());
        database.collection("food").document(food.getFoodId()).set(food);

        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);

        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("food", food);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
