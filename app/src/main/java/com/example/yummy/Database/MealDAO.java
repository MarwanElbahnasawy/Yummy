package com.example.yummy.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yummy.Model.MealsItem;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDAO {
    @Query("SELECT * FROM MealsItem")
    Flowable<List<MealsItem>> getStoredMealsItems();

    @Query("SELECT * FROM MealsItem WHERE strMeal LIKE :mealsItemName AND weekDay LIKE :weekDayString")
    MealsItem findMealByName(String mealsItemName, String weekDayString);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(MealsItem mealsItem);


    @Delete
    void deleteMeal(MealsItem mealsItem);

    @Query("DELETE FROM MealsItem")
    void deleteTableRoom();
}
