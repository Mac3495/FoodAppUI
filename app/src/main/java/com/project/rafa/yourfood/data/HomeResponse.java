package com.project.rafa.yourfood.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeResponse {
    @SerializedName("dish_list")
    private List<Food> dish_list;

    public List<Food> getDish_list() {
        return dish_list;
    }

    public void setDish_list(List<Food> dish_list) {
        this.dish_list = dish_list;
    }
}
