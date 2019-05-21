package com.project.rafa.yourfood.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.data.Food;
import com.project.rafa.yourfood.data.HomeResponse;
import com.project.rafa.yourfood.remote.ApiService;
import com.project.rafa.yourfood.remote.ApiUtils;

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
        Call<HomeResponse> request = mAPIService.recommend(nombre);

        request.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if(response.isSuccessful()){
                    try {

                        List<Food> foodList = response.body().getDish_list();

                        TextView nombre1 = (TextView) view.findViewById(R.id.nombre1);
                        ImageView link1 = (ImageView) view.findViewById(R.id.link1);
                        nombre1.setText(foodList.get(0).getName());
                        Glide.with(view).load(foodList.get(0).getImg()).into(link1);


                        TextView nombre2 = (TextView) view.findViewById(R.id.nombre2);
                        ImageView link2 = (ImageView) view.findViewById(R.id.link2);
                        nombre2.setText(foodList.get(1).getName());
                        Glide.with(view).load(foodList.get(1).getImg()).into(link2);


                        TextView nombre3 = (TextView) view.findViewById(R.id.nombre3);
                        ImageView link3 = (ImageView) view.findViewById(R.id.link3);
                        nombre3.setText(foodList.get(2).getName());
                        Glide.with(view).load(foodList.get(2).getImg()).into(link3);



                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
