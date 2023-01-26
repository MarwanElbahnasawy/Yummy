package com.example.yummy.SearchByCategory.Presenter;

import android.util.Log;



import com.example.yummy.Model.MealsItem;
import com.example.yummy.Network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PresenterMealFromSpecificCategory {
    InterfaceMealFromSpecificCategory interfaceMealFromSpecificCategory;
    List<MealsItem> meals = new ArrayList<>();
    private static final String TAG = "PresenterSearchByCatego";

    public PresenterMealFromSpecificCategory(InterfaceMealFromSpecificCategory interfaceMealFromSpecificCategory) {
        this.interfaceMealFromSpecificCategory = interfaceMealFromSpecificCategory;
    }


    public void getMealFromSpecificCategory(String categorySelected) {
        RetrofitClient.getInstance().getMyApi()
                .getMealsOfSelectedCategory(categorySelected)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        next -> {
                            meals = next.getMeals();


                        },
                        error -> {
                            Log.i(TAG, "getCategoryMeal: " + error.getMessage());
                        } ,
                        () -> {
                            interfaceMealFromSpecificCategory.responseOfDataOnSuccess(meals);

                        }
                );
    }


}
