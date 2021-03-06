package com.project.rafa.yourfood.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.data.Food;
import com.project.rafa.yourfood.data.HomeResponse;
import com.project.rafa.yourfood.remote.ApiService;
import com.project.rafa.yourfood.remote.ApiUtils;
import com.project.rafa.yourfood.ui.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendedFragment extends Fragment {

    private String titulo="", nombre="", ingrediente="", preparacion="", costo="";

    private ApiService mAPIService;


    public RecommendedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recommended, container, false);

        if (savedInstanceState == null) {
            Bundle extras = this.getArguments();
            if(extras != null) {
                titulo = extras.getString("titulo");
                nombre = extras.getString("nombre");
                ingrediente = extras.getString("ingrediente");
                preparacion = extras.getString("preparacion");
                costo = extras.getString("costo");
            }
        }

        TextView price = (TextView) v.findViewById(R.id.price);
        price.setText(costo + "Bs");

        rest(v);

        return v;
    }

    public void rest(final View view){
        mAPIService = ApiUtils.getApiServices();
//        Call<HomeResponse> request = mAPIService.recommend(nombre);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
//        Toast.makeText(getContext(), nombre+", "+userId, Toast.LENGTH_LONG).show();
        Call<HomeResponse> request = mAPIService.recommend(nombre, userId);

        request.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if(response.isSuccessful()){
                    try {

                        final List<Food> foodList = response.body().getDish_list();

//                        Toast.makeText(getContext(), String.valueOf(foodList.size()), Toast.LENGTH_LONG).show();

                        TextView nombre1 = (TextView) view.findViewById(R.id.nombre1);
                        ImageView link1 = (ImageView) view.findViewById(R.id.link1);
                        nombre1.setText(foodList.get(0).getName());
                        Glide.with(view).load(foodList.get(0).getImg()).into(link1);

                        CardView card1 = (CardView) view.findViewById(R.id.card1);
                        card1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                                intent.putExtra("food", foodList.get(0));
                                startActivity(intent);
                            }
                        });


                        TextView nombre2 = (TextView) view.findViewById(R.id.nombre2);
                        ImageView link2 = (ImageView) view.findViewById(R.id.link2);
                        nombre2.setText(foodList.get(1).getName());
                        Glide.with(view).load(foodList.get(1).getImg()).into(link2);

                        CardView card2 = (CardView) view.findViewById(R.id.card2);
                        card2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                                intent.putExtra("food", foodList.get(1));
                                startActivity(intent);
                            }
                        });



                        TextView nombre3 = (TextView) view.findViewById(R.id.nombre3);
                        ImageView link3 = (ImageView) view.findViewById(R.id.link3);
                        nombre3.setText(foodList.get(2).getName());
                        Glide.with(view).load(foodList.get(2).getImg()).into(link3);

                        CardView card3 = (CardView) view.findViewById(R.id.card3);
                        card3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                                intent.putExtra("food", foodList.get(2));
                                startActivity(intent);
                            }
                        });



                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }
                }
                else
                    Toast.makeText(getContext(), "Fallo algo", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
