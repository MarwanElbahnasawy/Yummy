package com.example.yummy.Network;

import com.example.yummy.SearchByArea.Model.RootAreasList;
import com.example.yummy.SearchByCategory.Models.RootCategoriesList;
import com.example.yummy.SearchByIngredient.Model.RootIngredientsList;
import com.example.yummy.Model.RootMeal;
import com.example.yummy.Model.RootSingleMeal;
import com.example.yummy.SearchByMeal.Model.RootMealsFromSingleLetter;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    //Base url for daily inspirations
    String BASE_URL_DAILY_INSPIRATIONS = "https://www.themealdb.com/api/json/v1/1/";


    @GET("random.php")
    Observable<RootMeal> getRootRandom();

    @GET("search.php")
    Observable<RootSingleMeal> getRootSingleMeal(@Query("s") String mealName);

    @GET("search.php")
    Observable<RootMealsFromSingleLetter> getRootMealsBySingleLetter(@Query("f") String firstLetterOfMeal);

    @GET("list.php?a=list")
    Observable<RootAreasList> getRootAreasList();

    @GET("list.php?c=list")
    Observable<RootCategoriesList> getRootCategoriesList();

    @GET("list.php?i=list")
    Observable<RootIngredientsList> getRootIngredientsList();

    @GET("filter.php")
    Observable<RootMeal> getMealsOfSelectedArea(@Query("a") String areaSelected);

    @GET("filter.php")
    Observable<RootMeal> getMealsOfSelectedCategory(@Query("c") String categorySelected);

    @GET("filter.php")
    Observable<RootMeal> getMealsOfSelectedIngredient(@Query("i") String ingredientSelected);

    @GET("lookup.php")
    Observable<RootMeal> getMealById(@Query("i") int parseInt);

    //www.themealdb.com/api/json/v1/1/search.php?f=a

    @GET("search.php")
    Observable<RootSingleMeal> getAllMeal(@Query("f") String mealName);


}
