package com.example.yummy.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.yummy.Model.MealsItem;

@Database(entities = MealsItem.class, exportSchema = false, version = 1)
public abstract class DB extends RoomDatabase {

    private static DB instance = null;

    public abstract MealDAO mealDAO();

    public static synchronized DB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DB.class, "Meal").build();
        }
        return instance;
    }

}
