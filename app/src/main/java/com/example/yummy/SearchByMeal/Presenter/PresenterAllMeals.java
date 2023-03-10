package com.example.yummy.SearchByMeal.Presenter;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.Repository.Model.RepositoryRemote;

import java.util.List;


public class PresenterAllMeals {
    private InterfaceAllMeals interfaceAllMeals;
    private List<MealsItem> meals;
    private RepositoryRemote repositoryRemote;

    public PresenterAllMeals(InterfaceAllMeals interfaceAllMeals) {
        this.interfaceAllMeals = interfaceAllMeals;
    }

    public void getAllMeals(Character s) {

        repositoryRemote = new RepositoryRemote(interfaceAllMeals);
        repositoryRemote.getAllMeals(s);


    }


}
