package com.project.rafa.yourfood.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment {


    private String titulo="", nombre="", ingrediente="", preparacion="", costo="", foodId="";
    private boolean fav = false;
    private ImageView like;
    public TextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
            }
        }
        TextView text_detail = (TextView) v.findViewById(R.id.text_detail);
        TextView price = (TextView) v.findViewById(R.id.price);
        like = (ImageView) v.findViewById(R.id.like);
        like.setAlpha(.3f);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        price.setText(costo + "Bs");
        if(titulo.compareTo("Preparacion") == 0){
            text_detail.setText(preparacion);
            like.setVisibility(View.GONE);
        }
        else {
            text_detail.setText(ingrediente);
            like.setVisibility(View.VISIBLE);
        }

        datos();

        return v;
    }


    List<FavoriteFood> list = new ArrayList<>();

    void datos(){

        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        final FirebaseFirestore database = FirebaseFirestore.getInstance();

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

    void update(){
        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        if(!fav) {
            FavoriteFood favs= new FavoriteFood();
            favs.setFoodId(foodId);
            favs.setuId(id);
            FirebaseFirestore.getInstance().collection("favorite").add(favs);
            like.setAlpha(1.0f);
        }
    }

}
