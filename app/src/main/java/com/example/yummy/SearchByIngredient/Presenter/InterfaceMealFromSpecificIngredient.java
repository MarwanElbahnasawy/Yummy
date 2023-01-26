package com.example.yummy.SearchByIngredient.Presenter;

import com.example.yummy.Model.MealsItem;

import java.util.List;

public interface InterfaceMealFromSpecificIngredient {
    public void responseOfDataOnSuccess(List<MealsItem> mealsList);
}
