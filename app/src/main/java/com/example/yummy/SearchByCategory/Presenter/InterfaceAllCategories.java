package com.example.yummy.SearchByCategory.Presenter;


import com.example.yummy.SearchByCategory.Models.EachCategoryModel;

import java.util.List;

public interface InterfaceAllCategories {
    public void responseOfDataOnSuccess(List<EachCategoryModel> categoriesReceived);
}
