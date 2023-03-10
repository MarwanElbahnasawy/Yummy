package com.example.yummy.SearchByIngredient.Presenter;


import com.example.yummy.Repository.Model.RepositoryRemote;

public class PresenterMealFromSpecificIngredient {
    private InterfaceMealFromSpecificIngredient interfaceMealFromSpecificIngredient;
    private static final String TAG = "PresenterMealFromSpecif";
    private RepositoryRemote repositoryRemote;

    public PresenterMealFromSpecificIngredient(InterfaceMealFromSpecificIngredient interfaceMealFromSpecificIngredient) {
        this.interfaceMealFromSpecificIngredient = interfaceMealFromSpecificIngredient;
    }

    public void getMealFromSpecificIngredient(String ingredientSelected) {

        repositoryRemote = new RepositoryRemote(interfaceMealFromSpecificIngredient);
        repositoryRemote.getMealFromSpecificIngredient(ingredientSelected);


    }


}
