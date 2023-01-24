package com.example.foodplanner.Network;

import com.example.foodplanner.Model.AreaListModel;
import com.example.foodplanner.Model.CategoryListModel;
import com.example.foodplanner.Model.GrediantListModel;
import com.example.foodplanner.Model.IngrdiantMealListModel;
import com.example.foodplanner.Model.Root;
import com.example.foodplanner.Model.RootSingleMeal;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    //Base url for daily inspirations
    String BASE_URL_DAILY_INSPIRATIONS = "https://www.themealdb.com/api/json/v1/1/";


    @GET("random.php")
    Observable<Root> getRootRandom();

    @GET("filter.php")
    Observable<Root> getRoot(@Query("a") String randomCountry);



    @GET("search.php")
    Observable<RootSingleMeal> getRootSingleMeal(@Query("s") String mealName);
    @GET("list.php?a=list")
    Observable<AreaListModel> getCountry();
    @GET("list.php?c=list")
    Observable<CategoryListModel> getCategory();
    @GET("list.php?i=list")
    Observable<GrediantListModel> getIngradiant();

    @GET("filter.php")
    Observable<Root> getCategoryMeal(@Query("c") String categoryMeal);
    @GET("filter.php")
    Observable<IngrdiantMealListModel> getIngrdiantMeal(@Query("i") String ingrediantMeal);




}
