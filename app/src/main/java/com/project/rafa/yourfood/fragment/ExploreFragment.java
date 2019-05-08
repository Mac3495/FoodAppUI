package com.project.rafa.yourfood.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.adapter.FoodAdapter;
import com.project.rafa.yourfood.data.Food;
import com.project.rafa.yourfood.ui.DetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExploreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment implements FoodAdapter.onFoodSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView feedRecycler;
    FoodAdapter foodAdapter;

    private OnFragmentInteractionListener mListener;

    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);

        feedRecycler = (RecyclerView) rootView.findViewById(R.id.rv_explore_fragment);

        feedRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        foodAdapter = new FoodAdapter(this);

        datos();

        feedRecycler.setAdapter(foodAdapter);

        return rootView;
    }

    void datos(){
        List<Food> list = new ArrayList<>();
        list.add(new Food("Hamburguejas al vapor", getString(R.string.large_text), getString(R.string.large_text), "Tipo 1", "", R.drawable.ham));
        list.add(new Food("La pizza de don cangrejo", getString(R.string.large_text), getString(R.string.large_text), "Tipo 2", "", R.drawable.pizza));
        list.add(new Food("No vives de ensalada", getString(R.string.large_text), getString(R.string.large_text), "Tipo 3", "", R.drawable.fru));
        list.add(new Food("Hamburguejas al vapor", getString(R.string.large_text), getString(R.string.large_text), "Tipo 1", "", R.drawable.ham));
        list.add(new Food("La pizza de don cangrejo",getString(R.string.large_text), getString(R.string.large_text), "Tipo 2", "", R.drawable.pizza));
        list.add(new Food("No vives de ensalada", getString(R.string.large_text), getString(R.string.large_text), "Tipo 3", "", R.drawable.fru));
        list.add(new Food("Hamburguejas al vapor", getString(R.string.large_text), getString(R.string.large_text), "Tipo 1", "", R.drawable.ham));

        foodAdapter.setDataset(list);
    }

    @Override
    public void onFoodSelected(Food food) {
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
        intent.putExtra("food", food);
        startActivity(intent);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
