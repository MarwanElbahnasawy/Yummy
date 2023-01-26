package com.example.yummy.SearchByIngredient.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummy.Model.MealsItem;
import com.example.yummy.Model.RootMeal;
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.R;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealFromSpecificIngredientAdapter extends RecyclerView.Adapter<MealFromSpecificIngredientAdapter.MyViewHolder> {
    List<MealsItem> meals;
    ViewGroup viewGroup;
    NavController navController;

    public MealFromSpecificIngredientAdapter(List<MealsItem> meals, NavController navController) {
        this.meals = meals;
        this.navController =navController;

    }

    @NonNull
    @Override
    public MealFromSpecificIngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewGroup =parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_meal_items, parent, false);
        return new MealFromSpecificIngredientAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealFromSpecificIngredientAdapter.MyViewHolder holder, int position) {
        Glide.with(holder.itemView).load(meals.get(position).getStrMealThumb()).into(holder.mealImage);
        holder.mealName.setText(meals.get(position).getStrMeal());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getMyApi().getMealById(Integer.parseInt(meals.get(position).getIdMeal()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RootMeal>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        // loading ui
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull RootMeal rootMeal) {
                        MealFromSpecificIngredient.searchTextInput.setText("");
                       navController
                                .navigate(MealFromSpecificIngredientDirections.
                    actionMealByIngrediantFragmentToMealDeatailsFragment(rootMeal.getMeals().get(0)));
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.area_meal);
            mealImage = itemView.findViewById(R.id.areaMeal_image);

        }
    }

}
