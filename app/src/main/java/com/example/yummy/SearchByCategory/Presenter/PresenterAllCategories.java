package com.example.yummy.SearchByCategory.Presenter;


import com.example.yummy.Repository.Model.RepositoryRemote;

public class PresenterAllCategories {
    InterfaceAllCategories interfaceAllCategories;
    private static final String TAG = "PresenterAllCategories";
    RepositoryRemote repositoryRemote;

    public PresenterAllCategories(InterfaceAllCategories interfaceAllCategories) {
        this.interfaceAllCategories = interfaceAllCategories;
    }

    public void getAllCategories() {

        repositoryRemote = new RepositoryRemote(interfaceAllCategories);
        repositoryRemote.getAllCategories();


    }


}
