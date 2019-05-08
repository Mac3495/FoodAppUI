package com.project.rafa.yourfood.data;

import java.io.Serializable;

public class Food implements Serializable {

    private String name;
    private String ingredients;
    private String preparation;
    private String type;
    private String foodId;
    private int img;

    public Food(){

    }

    public Food(String name, String ingredients, String preparation, String type, String foodId, int img) {
        this.name = name;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.type = type;
        this.foodId = foodId;
        this.img = img;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
