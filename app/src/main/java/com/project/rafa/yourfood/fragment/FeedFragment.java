package com.project.rafa.yourfood.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.project.rafa.yourfood.ui.DetailActivity;
import com.project.rafa.yourfood.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment implements FoodAdapter.onFoodSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView feedRecycler;
    FoodAdapter foodAdapter;
    List<Food> list = new ArrayList<>();
    List<Food> followingUsersDishes = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        feedRecycler = (RecyclerView) rootView.findViewById(R.id.rv_feed_fragment);

        feedRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        foodAdapter = new FoodAdapter(getActivity().getApplicationContext(), this);

//        datos();
        followedUsersDishes();

        feedRecycler.setAdapter(foodAdapter);

        return rootView;

    }

    void datos(){
        //list.clear();

        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        final FirebaseFirestore database = FirebaseFirestore.getInstance();

//        database.collection("follow").whereEqualTo("followerUserId", id).get()

        database.collection("food").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                list.clear();
                if(task.isSuccessful()){
                     for(DocumentSnapshot doc : task.getResult()){
                        Food food = doc.toObject(Food.class);
                        if (food.getUserId().equals(id))
                            continue;
                        list.add(food);
                    }

                    foodAdapter.setDataset(list);
                }
            }
        });
    }

    void followedUsersDishes(){
        //list.clear();

        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        final FirebaseFirestore database = FirebaseFirestore.getInstance();

//        database.collection("follow").whereEqualTo("followerUserId", id).get()
        final ArrayList<FollowUser> followLinks = new ArrayList<>();
        database.collection("follow").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                followLinks.clear();
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc : task.getResult()){
                        FollowUser followUser = doc.toObject(FollowUser.class);
                        if (followUser.getFollowerUserId().equals(id)){
                            followLinks.add(followUser);
                        }
                    }
                    list.clear();
                    for (FollowUser userLink: followLinks){
                        Log.i("link", userLink.getFollowedUserId());
                        database.collection("food").whereEqualTo("userId", userLink.getFollowedUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot doc : task.getResult()){
                                        Food food = doc.toObject(Food.class);
                                        list.add(food);
                                    }
                                    foodAdapter.setDataset(list);
                                }
                            }
                        });
                    }
                }
            }
        });
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
