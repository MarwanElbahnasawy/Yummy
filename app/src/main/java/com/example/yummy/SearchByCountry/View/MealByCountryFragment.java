package com.example.yummy.SearchByCountry.View;

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
import com.example.yummy.View.Adapters.MealsByCountriesAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealByCountryFragment extends Fragment {

    MealsByCountriesAdapter mealsByCountriesAdapter;
    RecyclerView recyclerView;
    TextInputEditText searchTextInput;
    List<MealsItem> mealsItemList = new ArrayList<>();
    private static final String TAG = "MealByCountryFragment";
    TextView tv_countrySelected;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rv_MealByCountry);
            searchTextInput=view.findViewById(R.id.textInput_Meal_search);
            String areaSelected = MealByCountryFragmentArgs.fromBundle(getArguments()).getArea();

        tv_countrySelected = view.findViewById(R.id.tv_countrySelected);
        tv_countrySelected.setText(areaSelected);


        RetrofitClient.getInstance().getMyApi()
                .getRoot(areaSelected)
                .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                    e->{
                mealsItemList =  e.getMeals();
                mealsByCountriesAdapter = new MealsByCountriesAdapter(mealsItemList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(mealsByCountriesAdapter);

            },
            error->{
                Log.i(TAG, "onViewCreated: " + error.getMessage());
            }
                        );
        searchTextInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                mealsByCountriesAdapter = new MealsByCountriesAdapter(mealsItemList.stream().filter(
                        mealsItem -> mealsItem.getStrMeal().startsWith(s.toString())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(mealsByCountriesAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//                .subscribe(new Observer<Root>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Root root) {
//                        mealsByCountriesAdapter = new MealsByCountriesAdapter(root.getMeals());
//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
//                        recyclerView.setLayoutManager(linearLayoutManager);
//
//                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

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
}