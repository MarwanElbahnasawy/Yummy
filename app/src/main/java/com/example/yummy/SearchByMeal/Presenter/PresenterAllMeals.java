package com.example.yummy.SearchByMeal.Presenter;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.SearchByMeal.Model.RootMealsFromSingleLetter;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PresenterAllMeals {
    private InterfaceAllMeals interfaceAllMeals;
    List<MealsItem> meals;

    public PresenterAllMeals(InterfaceAllMeals interfaceAllMeals) {
        this.interfaceAllMeals = interfaceAllMeals;
    }

    public void getAllMeals(CharSequence s){
        Observable<RootMealsFromSingleLetter> observable = RetrofitClient.getInstance().getMyApi().getRootMealsBySingleLetter(s.toString());
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {

                    if (response.getMeals() != null){
                        meals = response.getMeals();

                    }



                },
                error -> {
                    error.printStackTrace();
                } ,
                ()->{
                    interfaceAllMeals.responseOfDataOnSuccess(meals);
                }
        );
    }

}
