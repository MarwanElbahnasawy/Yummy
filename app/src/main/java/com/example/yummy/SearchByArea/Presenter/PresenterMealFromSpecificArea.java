package com.example.yummy.SearchByArea.Presenter;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.SearchByArea.View.MealFromSpecificAreaAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PresenterMealFromSpecificArea {
    InterfaceMealFromSpecificArea interfaceMealFromSpecificArea;
    List<MealsItem> meals = new ArrayList<>();
    private static final String TAG = "PresenterMealSpecificCa";

    public PresenterMealFromSpecificArea(InterfaceMealFromSpecificArea interfaceMealFromSpecificArea) {
        this.interfaceMealFromSpecificArea = interfaceMealFromSpecificArea;
    }

    public void getMealFromSpecificArea(String areaSelected) {
        RetrofitClient.getInstance().getMyApi()
                .getMealsOfSelectedArea(areaSelected)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        next->{

                            meals =  next.getMeals();


                        },
                        error->{
                            Log.i(TAG, "onViewCreated: " + error.getMessage());
                        } ,
                        () -> {
                            interfaceMealFromSpecificArea.responseOfDataOnSuccess(meals);
                        }
                );

    }


}
