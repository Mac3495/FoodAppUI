package com.project.rafa.yourfood.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.rafa.yourfood.R;
import com.project.rafa.yourfood.data.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    List<Food> dataset;
    onFoodSelectedListener onFoodSelectedListener;

    public interface onFoodSelectedListener{
        void onFoodSelected(Food food);
    }

    public FoodAdapter(onFoodSelectedListener onFoodSelectedListener) {
        this.dataset = new ArrayList<>();
        this.onFoodSelectedListener = onFoodSelectedListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_feed, viewGroup, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder foodViewHolder, int i) {
        Food food = dataset.get(i);
        foodViewHolder.imgFood.setImageResource(food.getImg());
        foodViewHolder.textFood.setText(food.getName());

        foodViewHolder.setOnFoodSelectedListener(food, onFoodSelectedListener );
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{

        View cardView;
        ImageView imgFood;
        TextView textFood;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_item_feed);
            imgFood = itemView.findViewById(R.id.img_item_feed);
            textFood = itemView.findViewById(R.id.text_item_feed);
        }

        public void setOnFoodSelectedListener(final Food food, final onFoodSelectedListener onFoodSelectedListener){
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onFoodSelectedListener.onFoodSelected(food);
                }
            });

        }
    }

    public void setDataset(List<Food> foods) {
        if (foods == null){
            dataset = new ArrayList<>();
        }
        else{
            dataset = foods;
        }
        notifyDataSetChanged();
    }
}
