package com.example.yummy.WeekPlanner.Presenter;

import android.content.Context;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.Repository.Model.RepositoryLocal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class PresenterWeekPlanner {
    private RepositoryLocal repositoryLocal;
    private Context context;


    public PresenterWeekPlanner(Context context) {
        this.context = context;
        repositoryLocal = new RepositoryLocal(context);
    }


    public Flowable<List<MealsItem>> returnStoredMealsItems() {
        return repositoryLocal.returnStoredMealsItems();
    }
}
