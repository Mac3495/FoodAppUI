package com.project.rafa.yourfood.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.rafa.yourfood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendedFragment extends Fragment {

    private String titulo="", nombre="", ingrediente="", preparacion="", costo="";


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

        return v;
    }

}
