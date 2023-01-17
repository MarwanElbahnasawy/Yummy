package com.example.foodplanner.Network;

import com.example.foodplanner.Model.Root;

import java.util.Random;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    //Base url for daily inspirations
    String BASE_URL_DAILY_INSPIRATIONS = "https://www.themealdb.com/api/json/v1/1/";



    @GET("filter.php")
    Call<Root> getRoot(@Query("a") String randomCountry);

    @GET("filter.php?a=Italian")
    Call<Root> getRootItalian();

    @GET("filter.php?a=Chinese")
    Call<Root> getRootChinese();

    @GET("filter.php?a=French")
    Call<Root> getRootFrench();

    @GET("filter.php?a=British")
    Call<Root> getRootBritish();

    @GET("filter.php?a=American")
    Call<Root> getRootAmerican();

    @GET("filter.php?a=Canadian")
    Call<Root> getRootCanadian();

}
