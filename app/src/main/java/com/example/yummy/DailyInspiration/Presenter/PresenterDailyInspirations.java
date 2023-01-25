package com.example.yummy.DailyInspiration.Presenter;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.Model.Root;
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.Model.SliderItemModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PresenterDailyInspirations {
    InterfaceDailyInspirations interfaceDailyInspirations;
    List<MealsItem> meals = new ArrayList<>();
    private List<SliderItemModel> sliderItemList;

    public PresenterDailyInspirations(InterfaceDailyInspirations interfaceDailyInspirations) {
        this.interfaceDailyInspirations = interfaceDailyInspirations;
    }


    public void getDailyInspirations() {

        String[] countriesList = {"Indian", "Italian", "Chinese", "French", "British"};
        String randomCountry = countriesList[(new Random()).nextInt(countriesList.length)];

        Observable<Root> observableRandom1 = RetrofitClient.getInstance().getMyApi().getRootRandom();
        Observable<Root> observableRandom2 = RetrofitClient.getInstance().getMyApi().getRootRandom();
        Observable<Root> observableRandom3 = RetrofitClient.getInstance().getMyApi().getRootRandom();
        Observable<Root> observableRandom4 = RetrofitClient.getInstance().getMyApi().getRootRandom();
        Observable<Root> observableRandom5 = RetrofitClient.getInstance().getMyApi().getRootRandom();

        ArrayList<Observable<Root>> arrayListObservablesRandomMeal = new ArrayList<>(Arrays.asList(observableRandom1, observableRandom2, observableRandom3, observableRandom4, observableRandom5));

        Observable<Root> combinedObservable = Observable.merge(arrayListObservablesRandomMeal);

        combinedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {

                            for(int i = 0 ; i<response.getMeals().size() ; i++){
                                meals.add(response.getMeals().get(i));
                            }

                        },

                        error -> {
                            interfaceDailyInspirations.responseOfDataOnFailure(error);
                        },
                        () -> {
                            interfaceDailyInspirations.responseOfDataOnSuccess(meals);
                        }
                );

    }



}
