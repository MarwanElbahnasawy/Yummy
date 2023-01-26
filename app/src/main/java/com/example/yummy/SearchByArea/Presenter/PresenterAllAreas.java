package com.example.yummy.SearchByArea.Presenter;


import com.example.yummy.Repository.Model.RepositoryRemote;

public class PresenterAllAreas {
    InterfaceAllAreas interfaceAllAreas;
    private static final String TAG = "PresenterAllCategories";
    RepositoryRemote repositoryRemote;

    public PresenterAllAreas(InterfaceAllAreas interfaceAllAreas) {
        this.interfaceAllAreas = interfaceAllAreas;
    }

    public void getAllAreas() {
        repositoryRemote = new RepositoryRemote(interfaceAllAreas);
        repositoryRemote.getAllAreas();


    }



}
