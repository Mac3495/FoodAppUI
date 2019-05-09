package com.project.rafa.yourfood.data;

public class FavoriteFood {

    private String uId;
    private String foodId;

    public FavoriteFood(){

    }

    public FavoriteFood(String uId, String foodId) {
        this.uId = uId;
        this.foodId = foodId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }
}
