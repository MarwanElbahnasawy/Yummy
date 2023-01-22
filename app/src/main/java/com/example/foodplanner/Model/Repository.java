package com.example.foodplanner.Model;

import android.content.Context;

import com.example.foodplanner.Database.DB;
import com.example.foodplanner.Database.MealDAO;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class Repository {
    private Context context;
    private MealDAO mealDAO;
    private Flowable<List<MealsItem>> storedMealsItems;

    public Repository(Context context){
        this.context=context;

        DB db= DB.getInstance(context);
        mealDAO=db.mealDAO();

        storedMealsItems= mealDAO.getStoredMealsItems();
    }

    public Flowable<List<MealsItem>> returnStoredMealsItems(){
        return storedMealsItems;

    }
    public void delete(MealsItem mealsItem){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deleteMeal(mealsItem);
            }
        }).start();
    }

    public void insert(long l, String email, String weekDay, MealsItem mealsItem){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //mealsItem.setCurrentTimeMillis(System.currentTimeMillis());
                mealsItem.setCurrentUserEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                mealsItem.setWeekDay(weekDay);
                mealDAO.insertMeal(mealsItem);
            }
        }).start();
    }

    public void deleteTableRoom(){
        mealDAO.deleteTableRoom();
    }

}
