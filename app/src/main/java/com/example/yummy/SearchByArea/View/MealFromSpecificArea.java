package com.example.yummy.SearchByArea.View;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.R;
import com.example.yummy.SearchByArea.Presenter.InterfaceMealFromSpecificArea;
import com.example.yummy.SearchByArea.Presenter.PresenterMealFromSpecificArea;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealFromSpecificArea extends Fragment implements InterfaceMealFromSpecificArea {

    MealFromSpecificAreaAdapter mealFromSpecificAreaAdapter;
    RecyclerView recyclerView;
    public static TextInputEditText searchTextInput;
    List<MealsItem> mealsItemList = new ArrayList<>();
    private static final String TAG = "MealFromSpecificArea";
    TextView tv_areaSelected;
    private PresenterMealFromSpecificArea presenterMealFromSpecificArea;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rv_MealByArea);
            searchTextInput=view.findViewById(R.id.textInput_Meal_search);
            String areaSelected = MealFromSpecificAreaArgs.fromBundle(getArguments()).getArea();

        tv_areaSelected = view.findViewById(R.id.tv_areaSelected);
        tv_areaSelected.setText(areaSelected);

        presenterMealFromSpecificArea = new PresenterMealFromSpecificArea(this);
        presenterMealFromSpecificArea.getMealFromSpecificArea(areaSelected);



        searchTextInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                mealFromSpecificAreaAdapter = new MealFromSpecificAreaAdapter(mealsItemList.stream().filter(
                        mealsItem -> mealsItem.getStrMeal().toLowerCase().startsWith(s.toString().toLowerCase())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(mealFromSpecificAreaAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    private void handlingRecyclerView() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_meal_by_country, container, false);
    }

    @Override
    public void responseOfDataOnSuccess(List<MealsItem> mealsListReceived) {
        mealsItemList = mealsListReceived;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mealFromSpecificAreaAdapter = new MealFromSpecificAreaAdapter(mealsItemList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mealFromSpecificAreaAdapter);
    }
}