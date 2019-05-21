package com.project.rafa.yourfood.data;

import org.json.JSONObject;

import java.io.Serializable;

public class Food extends JSONObject implements Serializable {

    private String name;
    private String ingredients;
    private String preparation;
    private String type;
    private String foodId;
    private String img;
    private String price;
    private String userId;

    public Food(){

    }

    public Food(String name, String ingredients, String preparation, String type, String foodId, String img, String price, String userId) {
        this.name = name;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.type = type;
        this.foodId = foodId;
        this.img = img;
        this.price = price;
        this.userId = userId;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
