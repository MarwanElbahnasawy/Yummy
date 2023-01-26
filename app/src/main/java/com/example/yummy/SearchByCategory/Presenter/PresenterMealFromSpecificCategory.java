package com.example.yummy.SearchByCategory.Presenter;


import com.example.yummy.Repository.Model.RepositoryRemote;



public class PresenterMealFromSpecificCategory {
    InterfaceMealFromSpecificCategory interfaceMealFromSpecificCategory;
    private static final String TAG = "PresenterSearchByCatego";
    RepositoryRemote repositoryRemote;

    public PresenterMealFromSpecificCategory(InterfaceMealFromSpecificCategory interfaceMealFromSpecificCategory) {
        this.interfaceMealFromSpecificCategory = interfaceMealFromSpecificCategory;
    }


    public void getMealFromSpecificCategory(String categorySelected) {

        repositoryRemote = new RepositoryRemote(interfaceMealFromSpecificCategory);
        repositoryRemote.getMealFromSpecificCategory(categorySelected);

    }




}
