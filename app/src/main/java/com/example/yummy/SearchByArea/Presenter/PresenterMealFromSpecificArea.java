package com.example.yummy.SearchByArea.Presenter;


import com.example.yummy.Model.MealsItem;
import com.example.yummy.Repository.Model.RepositoryRemote;

import java.util.ArrayList;
import java.util.List;

public class PresenterMealFromSpecificArea {
    private InterfaceMealFromSpecificArea interfaceMealFromSpecificArea;
    private List<MealsItem> meals = new ArrayList<>();
    private static final String TAG = "PresenterMealSpecificCa";
    private RepositoryRemote repositoryRemote;

    public PresenterMealFromSpecificArea(InterfaceMealFromSpecificArea interfaceMealFromSpecificArea) {
        this.interfaceMealFromSpecificArea = interfaceMealFromSpecificArea;
    }

    public void getMealFromSpecificArea(String areaSelected) {

        repositoryRemote = new RepositoryRemote(interfaceMealFromSpecificArea);
        repositoryRemote.getMealFromSpecificArea(areaSelected);


    }


}
