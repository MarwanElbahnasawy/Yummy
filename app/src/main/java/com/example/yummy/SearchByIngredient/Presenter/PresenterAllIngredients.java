package com.example.yummy.SearchByIngredient.Presenter;


import com.example.yummy.Repository.Model.RepositoryRemote;
import com.example.yummy.SearchByIngredient.Model.EachIngredientModel;

import java.util.List;

public class PresenterAllIngredients {
    private InterfaceAllIngredients interfaceAllIngredients;
    private List<EachIngredientModel> ingredients;
    private static final String TAG = "PresenterAllIngredients";
    private RepositoryRemote repositoryRemote;

    public PresenterAllIngredients(InterfaceAllIngredients interfaceAllIngredients) {
        this.interfaceAllIngredients = interfaceAllIngredients;
    }

    public void getAllIngredients() {


        repositoryRemote = new RepositoryRemote(interfaceAllIngredients);
        repositoryRemote.getAllIngredients();


    }


}
