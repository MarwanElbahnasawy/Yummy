package com.example.yummy.SearchByIngredient.Presenter;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.SearchByArea.Model.EachAreaModel;
import com.example.yummy.SearchByArea.Model.RootAreasList;
import com.example.yummy.SearchByIngredient.Model.EachIngredientModel;
import com.example.yummy.SearchByIngredient.Model.RootIngredientsList;
import com.example.yummy.SearchByIngredient.View.AllIngredientsAdapter;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PresenterAllIngredients {
    InterfaceAllIngredients interfaceAllIngredients;
    List<EachIngredientModel> ingredients;
    private static final String TAG = "PresenterAllIngredients";

    public PresenterAllIngredients(InterfaceAllIngredients interfaceAllIngredients) {
        this.interfaceAllIngredients = interfaceAllIngredients;
    }

    public void getAllIngredients() {
        Observable<RootIngredientsList> observableCategory = RetrofitClient.getInstance().getMyApi().getRootIngredientsList();

        observableCategory.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {
                    ingredients = response.getMeals();


                },
                error -> {
                    error.printStackTrace();
                } ,
                ()->{
                    interfaceAllIngredients.responseOfDataOnSuccess(ingredients);
                }
        );


    }

}
