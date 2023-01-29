package com.example.yummy.SearchByIngredient.Presenter;


import com.example.yummy.SearchByArea.Model.EachAreaModel;
import com.example.yummy.SearchByIngredient.Model.EachIngredientModel;

import java.util.List;

public interface InterfaceAllIngredients {
    public void responseOfDataOnSuccess(List<EachIngredientModel> ingredientsReceived);
}
