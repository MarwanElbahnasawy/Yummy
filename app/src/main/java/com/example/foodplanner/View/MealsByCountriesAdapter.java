package com.example.foodplanner.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Model.MealsItem;
import com.example.foodplanner.R;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

public class MealsByCountriesAdapter  extends RecyclerView.Adapter<MealsByCountriesAdapter.MyViewHolder> {
    List<MealsItem> meals;

    public MealsByCountriesAdapter(List<MealsItem> meals) {
        this.meals=meals;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_meal_items, parent, false);
        return new MealsByCountriesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(holder.itemView).load(meals.get(position).getStrMealThumb()).into(holder.mealImage);
        holder.mealName.setText(meals.get(position).getStrMeal());

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
                TextView mealName;
                ImageView mealImage;
        public MyViewHolder(@NonNull View itemView) {
        super(itemView);
            mealName=itemView.findViewById(R.id.country_meal);
            mealImage=itemView.findViewById(R.id.countryMeal_image);

        }
    }
}
