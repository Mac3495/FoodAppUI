package com.project.rafa.yourfood.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.adapter.FoodAdapter;
import com.project.rafa.yourfood.data.Food;
import com.project.rafa.yourfood.data.FoodUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements FoodAdapter.onFoodSelectedListener{

    TextView name, description;
    RecyclerView recyclerView;
    FoodAdapter adapter;

    final FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("user");

        name = findViewById(R.id.tv_username);
        description = findViewById(R.id.tv_description);
        recyclerView = findViewById(R.id.rv_profile_activity);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FoodAdapter(this, this);

        datos(user);

        recyclerView.setAdapter(adapter);

        database.collection("user").whereEqualTo("userId", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc : task.getResult()){
                        FoodUser foodUser = doc.toObject(FoodUser.class);
                        name.setText(foodUser.getUser());
                        description.setText(foodUser.getDescription());
                    }
                }
            }
        });


    }

    void datos(String user){
        final List<Food> list = new ArrayList<>();
        database.collection("food").whereEqualTo("userId", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    list.clear();
                    for(DocumentSnapshot doc : task.getResult()){
                        Food food = doc.toObject(Food.class);
                        list.add(food);
                    }
                    adapter.setDataset(list);
                }
            }
        });
    }

    @Override
    public void onFoodSelected(Food food) {

    }
}
