package com.example.yummy.Favorites.Presenter;

import android.content.Context;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.Repository.Model.RepositoryLocal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class PresenterFavoriteMeals {
    Context context;
    RepositoryLocal repositoryLocal;


    public PresenterFavoriteMeals(Context context) {
        this.context = context;
        repositoryLocal = new RepositoryLocal(context);
    }

    public Flowable<List<MealsItem>> returnStoredMealsItems() {
        return repositoryLocal.returnStoredMealsItems();
    }
}
