package com.example.yummy.SearchByCategory.Presenter;

import android.util.Log;

import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.SearchByCategory.Models.RootCategoriesList;
import com.example.yummy.SearchByCategory.Models.EachCategoryModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PresenterAllCategories {
    InterfaceAllCategories interfaceAllCategories;
    List<EachCategoryModel> categories;
    private static final String TAG = "PresenterAllCategories";

    public PresenterAllCategories(InterfaceAllCategories interfaceAllCategories) {
        this.interfaceAllCategories = interfaceAllCategories;
    }

    public void getAllCategories() {
        Observable<RootCategoriesList> observableCategory = RetrofitClient.getInstance().getMyApi().getRootCategoriesList();

        observableCategory.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {
                    Log.i(TAG, "getAllCategories: " + response.getCategories());
                    categories = response.getCategories();
                    Log.i(TAG, "getAllCategories:---------------- " + categories.size());



                },
                error -> {
                    error.printStackTrace();
                } ,
                () -> {
                    interfaceAllCategories.responseOfDataOnSuccess(categories);
                }
        );
    }

}
