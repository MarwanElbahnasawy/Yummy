package com.example.yummy.DailyInspiration.Presenter;

import com.example.yummy.Model.MealsItem;

import java.util.List;

public interface InterfaceDailyInspirations {
    public void responseOfDataOnSuccess(List<MealsItem> mealsList);
    public void responseOfDataOnFailure(Throwable error);
}
