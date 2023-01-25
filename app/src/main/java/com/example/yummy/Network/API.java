package com.example.yummy.Network;

import com.example.yummy.SearchByCountry.Model.AreaListModel;
import com.example.yummy.SearchByCategory.View.CategoryListModel;
import com.example.yummy.SearchByIngredient.Model.IngredientListModel;
import com.example.yummy.SearchByIngredient.Model.IngredientMealListModel;
import com.example.yummy.Model.Root;
import com.example.yummy.Model.RootSingleMeal;
import com.example.yummy.SearchGeneral.Model.RootMealsFromSingleLetter;

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

    @GET("search.php")
    Observable<RootMealsFromSingleLetter> getRootMealsBySingleLetter(@Query("f") String firstLetterOfMeal);

    @GET("list.php?a=list")
    Observable<AreaListModel> getCountry();
    @GET("list.php?c=list")
    Observable<CategoryListModel> getCategory();
    @GET("list.php?i=list")
    Observable<IngredientListModel> getIngradiant();

    @GET("filter.php")
    Observable<Root> getCategoryMeal(@Query("c") String categoryMeal);
    @GET("filter.php")
    Observable<IngredientMealListModel> getIngrdiantMeal(@Query("i") String ingrediantMeal);

   @GET("lookup.php")
    Observable<Root> getMealById(@Query("i") int parseInt);

   //www.themealdb.com/api/json/v1/1/search.php?f=a

    @GET("search.php")
    Observable<RootSingleMeal> getAllMeal(@Query("f") String mealName);



}
