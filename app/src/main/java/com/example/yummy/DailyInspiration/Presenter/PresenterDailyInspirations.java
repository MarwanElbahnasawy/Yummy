package com.example.yummy.DailyInspiration.Presenter;

import android.content.Context;

import com.example.yummy.MainActivity.View.MainActivity;
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
        repositoryRemote.getDailyInspirations();
    }

    public Flowable<List<MealsItem>> returnStoredMealsItems() {
        repositoryLocal = new RepositoryLocal(context);
        return repositoryLocal.returnStoredMealsItems();
    }

    public void loadRoomFromFirestore() {
        repositoryLocal = new RepositoryLocal(interfaceDailyInspirations, context);
        repositoryLocal.loadRoomFromFirestore();
    }


    public void loadHeaderTitle() {
        repositoryRemote = new RepositoryRemote();
        repositoryRemote.changeHeaderTitle();
    }
}
