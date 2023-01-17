package com.example.foodplanner.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Root {
    @SerializedName("meals")
    private List<MealsItem> meals;

    public List<MealsItem> getMeals(){
        return meals;
    }
}
