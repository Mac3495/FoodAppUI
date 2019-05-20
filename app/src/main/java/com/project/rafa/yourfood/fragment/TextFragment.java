package com.project.rafa.yourfood.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.data.FavoriteFood;
import com.project.rafa.yourfood.data.Food;
import com.project.rafa.yourfood.data.FoodUser;
import com.project.rafa.yourfood.ui.LoginActivity;
import com.project.rafa.yourfood.ui.MainActivity;
import com.project.rafa.yourfood.ui.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment {


    private String titulo="", nombre="", ingrediente="", preparacion="", costo="", foodId="", user="";
    private boolean fav = false;
    private ImageView like;
    String myUserId;
    Button btn_delete;

    final FirebaseFirestore database = FirebaseFirestore.getInstance();

    public TextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_text, container, false);


        if (savedInstanceState == null) {
            Bundle extras = this.getArguments();
            if(extras != null) {
                titulo = extras.getString("titulo");
                nombre = extras.getString("nombre");
                ingrediente = extras.getString("ingrediente");
                preparacion = extras.getString("preparacion");
                costo = extras.getString("costo");
                foodId = extras.getString("foodId");
                user = extras.getString("user");
            }
        }

        btn_delete = (Button) v.findViewById(R.id.btn_delete);
        TextView text_detail = (TextView) v.findViewById(R.id.text_detail);
        TextView price = (TextView) v.findViewById(R.id.price);
        final TextView usert = v.findViewById(R.id.user);
        like = (ImageView) v.findViewById(R.id.like);
        like.setAlpha(.3f);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                update();

                Boolean isFav = checkFavourite();
                if (isFav)
                    dislikeFood();
                else
                    likeFood();
            }
        });
        price.setText(costo + "Bs");



        database.collection("user").whereEqualTo("userId", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc : task.getResult()){
                        FoodUser foodUser = doc.toObject(FoodUser.class);
                        usert.setText("Por: " + foodUser.getUser());
                    }
                }
            }
        });


        usert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ProfileActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        if(titulo.compareTo("Preparacion") == 0){
            text_detail.setText(preparacion);
            like.setVisibility(View.GONE);
        }
        else {
            text_detail.setText(ingrediente);
            like.setVisibility(View.VISIBLE);
        }

        // Hide Delete Button if user is not owner of the dish.
        myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        if (!myUserId.equals(user)) {
            btn_delete.setVisibility(View.INVISIBLE);
        }
        else {
            like.setVisibility(View.INVISIBLE);

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDish();
                }
            });
        }

        datos();

        return v;
    }


    List<FavoriteFood> list = new ArrayList<>();

    void datos(){

        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        database.collection("favorite").whereEqualTo("uId", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                list.clear();
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc : task.getResult()){
                        FavoriteFood food = doc.toObject(FavoriteFood.class);
                        if(food.getFoodId().equals(foodId)) {
                            fav = true;
                            like.setAlpha(1.0f);
                        }
                        list.add(food);

                    }
                }
            }
        });


    }

    Boolean checkFavourite(){
        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        database.collection("favorite").whereEqualTo("uId", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    fav = false;
                    for (DocumentSnapshot doc : task.getResult()) {
                        FavoriteFood favoriteFood = doc.toObject(FavoriteFood.class);
                        if (favoriteFood.getFoodId().equals(foodId)){
                            fav = true;
                            break;
                        }
                    }
                }
            }
        });
        return fav;
    }

    void likeFood(){
        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        final FavoriteFood favs = new FavoriteFood();
        favs.setFoodId(foodId);
        favs.setuId(id);

        FirebaseFirestore.getInstance().collection("favorite").add(favs);
        like.setAlpha(1.0f);
    }

    void dislikeFood(){
        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        final FavoriteFood favs = new FavoriteFood();
        favs.setFoodId(foodId);
        favs.setuId(id);

        database.collection("favorite").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        FavoriteFood favoriteFood = doc.toObject(FavoriteFood.class);
                        if (favoriteFood.getFoodId().equals(favs.getFoodId()) && favoriteFood.getuId().equals(favs.getuId())){
                            String docId = doc.getId();
                            database.collection("favorite").document(docId).delete();
                            like.setAlpha(.3f);
                        }
                    }
                }
            }
        });
    }

    void deleteDish(){
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        database.collection("food").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        Food dish = doc.toObject(Food.class);
                        if (dish.getFoodId().equals(foodId)){
                            String docId = doc.getId();
                            database.collection("food").document(docId).delete();
//                            getActivity().onBackPressed();
//                            getActivity().finish();
                            Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }
}
