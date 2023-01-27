package com.example.yummy.DailyInspiration.Presenter;

import android.content.Context;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.Repository.Model.RepositoryLocal;
import com.example.yummy.Repository.Model.RepositoryRemote;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;


public class PresenterDailyInspirations {
    InterfaceDailyInspirations interfaceDailyInspirations;
    List<MealsItem> meals = new ArrayList<>();
    private RepositoryRemote repositoryRemote;
    private RepositoryLocal repositoryLocal;
    Context context;


    public PresenterDailyInspirations(InterfaceDailyInspirations interfaceDailyInspirations, Context context) {
        this.interfaceDailyInspirations = interfaceDailyInspirations;
        this.context = context;
    }

    public void getDailyInspirations() {
        repositoryRemote = new RepositoryRemote(interfaceDailyInspirations);
        repositoryLocal = new RepositoryLocal(context);
        repositoryRemote.getDailyInspirations();
    }

    public Flowable<List<MealsItem>> returnStoredMealsItems() {
        return repositoryLocal.returnStoredMealsItems();
    }


}
