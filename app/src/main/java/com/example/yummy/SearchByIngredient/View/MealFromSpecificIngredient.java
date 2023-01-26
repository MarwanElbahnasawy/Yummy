package com.example.yummy.SearchByIngredient.View;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.R;
import com.example.yummy.SearchByIngredient.Presenter.InterfaceMealFromSpecificIngredient;
import com.example.yummy.SearchByIngredient.Presenter.PresenterMealFromSpecificIngredient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealFromSpecificIngredient extends Fragment implements InterfaceMealFromSpecificIngredient {

    MealFromSpecificIngredientAdapter mealFromSpecificIngredientAdapter;
    RecyclerView recyclerView;
    public static TextInputEditText searchTextInput;
    List<MealsItem> mealsItemList = new ArrayList<>();
    private static final String TAG = "MealByCategory";
    TextView tv_ingredientSelected;
    private PresenterMealFromSpecificIngredient presenterMealFromSpecificIngredient;

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
        tv_ingredientSelected = view.findViewById(R.id.tv_ingredientSelected);

        String ingrediantSelected = MealFromSpecificIngredientArgs.fromBundle(getArguments()).getIngradiant();

        tv_ingredientSelected.setText(ingrediantSelected);

        presenterMealFromSpecificIngredient = new PresenterMealFromSpecificIngredient(this);
        presenterMealFromSpecificIngredient.getMealFromSpecificIngredient(ingrediantSelected);


        searchTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mealFromSpecificIngredientAdapter = new MealFromSpecificIngredientAdapter(mealsItemList.stream().filter(
                        mealsItem -> mealsItem.getStrMeal().toLowerCase().startsWith(s.toString().toLowerCase())).collect(Collectors.toList()), NavHostFragment.findNavController(MealFromSpecificIngredient.this));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(mealFromSpecificIngredientAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void responseOfDataOnSuccess(List<MealsItem> mealsListReceived) {
        mealsItemList = mealsListReceived;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mealFromSpecificIngredientAdapter = new MealFromSpecificIngredientAdapter(mealsItemList, NavHostFragment.findNavController(this));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mealFromSpecificIngredientAdapter);
    }
}