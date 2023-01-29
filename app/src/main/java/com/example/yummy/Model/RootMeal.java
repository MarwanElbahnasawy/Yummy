package com.example.yummy.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootMeal {
    @SerializedName("meals")
    private List<MealsItem> meals;

    public List<MealsItem> getMeals() {
        return meals;
    }
}
