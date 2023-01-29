package com.example.yummy.SearchByIngredient.View;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.yummy.R;
import com.example.yummy.SearchByIngredient.Presenter.InterfaceMealFromSpecificIngredient;
import com.example.yummy.SearchByIngredient.Presenter.PresenterMealFromSpecificIngredient;
import com.example.yummy.Utility.NetworkChecker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealsFromSpecificIngredient extends Fragment implements InterfaceMealFromSpecificIngredient {

    MealsFromSpecificIngredientAdapter mealsFromSpecificIngredientAdapter;
    RecyclerView recyclerView;
    public static TextInputEditText searchTextInput;
    List<MealsItem> mealsItemList = new ArrayList<>();
    private static final String TAG = "MealByCategory";
    TextView tv_ingredientSelected;
    private PresenterMealFromSpecificIngredient presenterMealFromSpecificIngredient;
    private NetworkChecker networkChecker = NetworkChecker.getInstance();

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

        String ingrediantSelected = MealsFromSpecificIngredientArgs.fromBundle(getArguments()).getIngradiant();

        tv_ingredientSelected.setText(ingrediantSelected);

        presenterMealFromSpecificIngredient = new PresenterMealFromSpecificIngredient(this);
        presenterMealFromSpecificIngredient.getMealFromSpecificIngredient(ingrediantSelected);


        searchTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mealsFromSpecificIngredientAdapter = new MealsFromSpecificIngredientAdapter(mealsItemList.stream().filter(
                        mealsItem -> mealsItem.getStrMeal().toLowerCase().contains(s.toString().toLowerCase())).collect(Collectors.toList()), NavHostFragment.findNavController(MealsFromSpecificIngredient.this));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(mealsFromSpecificIngredientAdapter);


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
        mealsFromSpecificIngredientAdapter = new MealsFromSpecificIngredientAdapter(mealsItemList, NavHostFragment.findNavController(this));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mealsFromSpecificIngredientAdapter);
    }
}