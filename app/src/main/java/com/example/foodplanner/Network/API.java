package com.example.foodplanner.Network;

import com.example.foodplanner.Model.Root;
import com.example.foodplanner.Model.RootSingleMeal;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    //Base url for daily inspirations
    String BASE_URL_DAILY_INSPIRATIONS = "https://www.themealdb.com/api/json/v1/1/";


//    @GET("filter.php")
//    Call<Root> getRoot(@Query("a") String randomCountry);

    @GET("filter.php")
    Observable<Root> getRoot(@Query("a") String randomCountry);

    @GET("search.php")
    Observable<RootSingleMeal> getRootSingleMeal(@Query("s") String mealName);


}
