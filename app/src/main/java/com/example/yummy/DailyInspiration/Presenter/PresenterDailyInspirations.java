package com.example.yummy.DailyInspiration.Presenter;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.Repository.Model.RepositoryRemote;
import java.util.ArrayList;
import java.util.List;


public class PresenterDailyInspirations {
    InterfaceDailyInspirations interfaceDailyInspirations;
    List<MealsItem> meals = new ArrayList<>();
    private RepositoryRemote repositoryRemote;


    public PresenterDailyInspirations(InterfaceDailyInspirations interfaceDailyInspirations) {
        this.interfaceDailyInspirations = interfaceDailyInspirations;
    }

    public void getDailyInspirations() {
        repositoryRemote = new RepositoryRemote(interfaceDailyInspirations);
        repositoryRemote.getDailyInspirations();
    }






}
