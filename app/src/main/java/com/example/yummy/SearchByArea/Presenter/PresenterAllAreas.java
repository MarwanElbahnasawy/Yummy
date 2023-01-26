package com.example.yummy.SearchByArea.Presenter;

import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.SearchByArea.Model.EachAreaModel;
import com.example.yummy.SearchByArea.Model.RootAreasList;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PresenterAllAreas {
    InterfaceAllAreas interfaceAllAreas;
    List<EachAreaModel> areas;
    private static final String TAG = "PresenterAllCategories";

    public PresenterAllAreas(InterfaceAllAreas interfaceAllAreas) {
        this.interfaceAllAreas = interfaceAllAreas;
    }

    public void getAllAreas() {
        Observable<RootAreasList> observableAreas = RetrofitClient.getInstance().getMyApi().getRootAreasList();
        observableAreas.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {
                    areas = response.getMeals();


                },
                error -> {
                    error.printStackTrace();
                } ,
                () -> {
                    interfaceAllAreas.responseOfDataOnSuccess(areas);
                }
        );

    }

}
