package com.example.yummy.SearchByMeal.Presenter;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.Repository.Model.RepositoryRemote;
import java.util.List;


public class PresenterAllMeals {
    private InterfaceAllMeals interfaceAllMeals;
    List<MealsItem> meals;
    RepositoryRemote repositoryRemote;

    public PresenterAllMeals(InterfaceAllMeals interfaceAllMeals) {
        this.interfaceAllMeals = interfaceAllMeals;
    }

    public void getAllMeals(CharSequence s){

        repositoryRemote = new RepositoryRemote(interfaceAllMeals);
        repositoryRemote.getAllMeals(s);




    }



}
