package com.example.foodplanner.Controller.Fragments.MainFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Model.IngrdiantMealModel;
import com.example.foodplanner.Network.RetrofitClient;
import com.example.foodplanner.R;
import com.example.foodplanner.View.MealByIngrdiantAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealByIngrediantFragment extends Fragment {

    MealByIngrdiantAdapter mealByIngrdiantAdapter;
    RecyclerView recyclerView;
    TextInputEditText searchTextInput;
    List<IngrdiantMealModel> mealsItemList = new ArrayList<>();
    private static final String TAG = "MealByCategory";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_by_ingrediant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_MealByIngradiant);
        searchTextInput = view.findViewById(R.id.textInput_Meal_search_Ingradiant);

        String ingrediant = MealByIngrediantFragmentArgs.fromBundle(getArguments()).getIngradiant();
        RetrofitClient.getInstance().getMyApi()
                .getIngrdiantMeal(ingrediant)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        e -> {
                          //  Log.e(TAG, "onViewCreated: "+MealByIngrediantFragmentDirections.actionMealByIngrediantFragmentToMealDeatailsFragment(null));
                            mealsItemList = e.getMeals();
                            mealByIngrdiantAdapter = new MealByIngrdiantAdapter(mealsItemList, NavHostFragment.findNavController(this));

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(mealByIngrdiantAdapter);

                        },
                        error -> {
                            Log.i(TAG, "onViewCreated: " + error.getMessage());
                        }
                );
        searchTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mealByIngrdiantAdapter = new MealByIngrdiantAdapter(mealsItemList.stream().filter(
                        mealsItem -> mealsItem.getStrMeal().startsWith(s.toString())).collect(Collectors.toList()), NavHostFragment.findNavController(MealByIngrediantFragment.this));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(mealByIngrdiantAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}