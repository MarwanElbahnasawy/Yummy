package com.example.yummy.Repository.Presenter;

import android.content.Context;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.Repository.Model.Repository;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class PresenterRepository {
    Repository rep;

    public PresenterRepository(Context context) {
        rep = new Repository(context);
    }


    public Flowable<List<MealsItem>> returnStoredMealsItems(){
        return rep.returnStoredMealsItems();
    }

    public void delete(MealsItem mealsItem){
        rep.delete(mealsItem);
    }

    public void insert(MealsItem mealsItem, String weekDay, String documentID){
        rep.insert(mealsItem, weekDay, documentID);
    }

    public MealsItem findMealByName(String mealName, String weekDayString){
        return rep.findMealByName(mealName, weekDayString);
    }

    public void loadRoomFromFirestore(String email){
        rep.loadRoomFromFirestore(email);
    }

    public void deleteTableRoom(){
        rep.deleteTableRoom();
    }


}
