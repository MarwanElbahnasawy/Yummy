package com.example.yummy.SearchByIngredient.Presenter;

import android.util.Log;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.SearchByIngredient.View.MealFromSpecificIngredientAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PresenterMealFromSpecificIngredient {
    InterfaceMealFromSpecificIngredient interfaceMealFromSpecificIngredient;
    List<MealsItem> meals = new ArrayList<>();
    private static final String TAG = "PresenterMealFromSpecif";

    public PresenterMealFromSpecificIngredient(InterfaceMealFromSpecificIngredient interfaceMealFromSpecificIngredient) {
        this.interfaceMealFromSpecificIngredient = interfaceMealFromSpecificIngredient;
    }

    public void getMealFromSpecificIngredient(String ingredientSelected) {
        RetrofitClient.getInstance().getMyApi()
                .getMealsOfSelectedIngredient(ingredientSelected)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        next -> {
                            //  Log.e(TAG, "onViewCreated: "+MealByIngrediantFragmentDirections.actionMealByIngrediantFragmentToMealDeatailsFragment(null));
                            meals = next.getMeals();




                        },
                        error -> {
                            Log.i(TAG, "onViewCreated: " + error.getMessage());
                        } ,
                        () -> {
                            interfaceMealFromSpecificIngredient.responseOfDataOnSuccess(meals);
                        }
                );


    }


}
