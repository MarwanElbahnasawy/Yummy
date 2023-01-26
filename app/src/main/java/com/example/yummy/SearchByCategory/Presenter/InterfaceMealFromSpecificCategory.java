package com.example.yummy.SearchByCategory.Presenter;

import com.example.yummy.Model.MealsItem;

import java.util.List;

public interface InterfaceMealFromSpecificCategory {
    public void responseOfDataOnSuccess(List<MealsItem> mealsList);
}
