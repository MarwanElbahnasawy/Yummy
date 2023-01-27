package com.example.yummy.SearchByCategory.View;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.R;
import com.example.yummy.SearchByCategory.Presenter.InterfaceMealFromSpecificCategory;
import com.example.yummy.SearchByCategory.Presenter.PresenterMealFromSpecificCategory;
import com.example.yummy.Utility.NetworkChecker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealsFromSpecificCategory extends Fragment implements InterfaceMealFromSpecificCategory {
    MealsFromSpecificCategoryAdapter mealsFromSpecificCategoryAdapter;
    RecyclerView recyclerView;
    public static TextInputEditText searchTextInput;
    List<MealsItem> mealsItemList = new ArrayList<>();
    private static final String TAG = "MealByCategory";
    TextView tv_categorySelected;
    PresenterMealFromSpecificCategory presenterMealFromSpecificCategory;
    private NetworkChecker networkChecker = NetworkChecker.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_by_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_MealByCategory);
        searchTextInput = view.findViewById(R.id.textInput_Meal_search_category);
        tv_categorySelected = view.findViewById(R.id.tv_categorySelected);

        String categorySelected = MealsFromSpecificCategoryArgs.fromBundle(getArguments()).getCategory();

        tv_categorySelected.setText(categorySelected);

        presenterMealFromSpecificCategory = new PresenterMealFromSpecificCategory(this);
        presenterMealFromSpecificCategory.getMealFromSpecificCategory(categorySelected);




        searchTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mealsFromSpecificCategoryAdapter = new MealsFromSpecificCategoryAdapter(mealsItemList.stream().filter(
                        mealsItem -> mealsItem.getStrMeal().toLowerCase().startsWith(s.toString().toLowerCase())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(mealsFromSpecificCategoryAdapter);



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
        recyclerView.setLayoutManager(linearLayoutManager);
        mealsFromSpecificCategoryAdapter = new MealsFromSpecificCategoryAdapter(mealsItemList);
        recyclerView.setAdapter(mealsFromSpecificCategoryAdapter);
    }

}