package com.project.rafa.yourfood.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.adapter.FoodAdapter;
import com.project.rafa.yourfood.data.FollowUser;
import com.project.rafa.yourfood.data.Food;
import com.project.rafa.yourfood.data.FoodUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements FoodAdapter.onFoodSelectedListener{

    TextView name, description;
    RecyclerView recyclerView;
    FoodAdapter adapter;
    Button followButton;

    Boolean following;
    String myUserId;

    final FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        final String user = bundle.getString("user");

        name = findViewById(R.id.tv_username);
        description = findViewById(R.id.tv_description);
        recyclerView = findViewById(R.id.rv_profile_activity);
        followButton = findViewById(R.id.btn_follow);

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

        myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        if (myUserId.equals(user)) {
            followButton.setVisibility(View.INVISIBLE);
        }
        else{
            checkFollowing(user);

            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean temp = checkFollowing(user);
                    if (temp)
                        unfollowUser(user);
                    else
                        followUser(user);
                }
            });
        }
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

    public void followUser(String followedUserId){
        final String followerUserId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        FollowUser followUser = new FollowUser(followedUserId, followerUserId);
        FirebaseFirestore.getInstance().collection("follow").add(followUser);

        followButton.setText("No Seguir");
    }

    public void unfollowUser(final String followedUserId){
        final String followerUserId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        database.collection("follow").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        FollowUser followUser = doc.toObject(FollowUser.class);
                        if (followUser.getFollowerUserId().equals(followerUserId) && followUser.getFollowedUserId().equals(followedUserId)){
                            String docId = doc.getId();
                            database.collection("follow").document(docId).delete();
                            followButton.setText("Seguir");
                        }
                    }
                }
            }
        });
    }

    public Boolean checkFollowing(final String followedUserId) {
        final String followerUserId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        database.collection("follow").whereEqualTo("followerUserId", followerUserId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    following = false;
                    for (DocumentSnapshot doc : task.getResult()) {
                        FollowUser followUser = doc.toObject(FollowUser.class);
                        if (followUser.getFollowedUserId().equals(followedUserId)){
                            followButton.setText("No Seguir");
                            following = true;
                            break;
                        }
                    }
                }
            }
        });
        return following;
    }
}
