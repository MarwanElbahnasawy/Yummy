package com.example.yummy.Controller.Fragments.MainFragments;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.Model.AreaListModel;
import com.example.yummy.Model.MealsItem;
import com.example.yummy.Model.Root;
import com.example.yummy.Model.RootSingleMeal;
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.R;
import com.example.yummy.View.AllMealsAdapter;
import com.example.yummy.View.CountryAdapter;
import com.example.yummy.View.MealsByCountriesAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchByAllMealsFragment extends Fragment {

    AllMealsAdapter allMealsAdapter;
    RecyclerView recyclerView;
    TextInputEditText searchTextInput;
    List<MealsItem> mealsItemList = new ArrayList<>();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_allMeals);
        searchTextInput=view.findViewById(R.id.tinput_search_AllMeals);

        //String[] MealsList = {"c", "b", "d", "e", "f","g","h","l","o","k","t","p","w"};

        Observable<RootSingleMeal> observable1 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("a");
        Observable<RootSingleMeal> observable2 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("b");
        Observable<RootSingleMeal> observable3 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("c");
        Observable<RootSingleMeal> observable4 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("d");
        Observable<RootSingleMeal> observable5 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("e");
        Observable<RootSingleMeal> observable6 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("f");
        Observable<RootSingleMeal> observable7 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("g");
        Observable<RootSingleMeal> observable8 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("h");

        Observable<RootSingleMeal> observable11 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("i");
//        Observable<RootSingleMeal> observable12 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("j");
//        Observable<RootSingleMeal> observable13 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("k");
//        Observable<RootSingleMeal> observable14 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("l");
//        Observable<RootSingleMeal> observable15 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("m");
//        Observable<RootSingleMeal> observable16 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("n");
//        Observable<RootSingleMeal> observable17 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("o");
//        Observable<RootSingleMeal> observable18 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("p");
//
//
//        Observable<RootSingleMeal> observable19 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("q");
//        Observable<RootSingleMeal> observable20 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("r");
//        Observable<RootSingleMeal> observable21 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("s");
//        Observable<RootSingleMeal> observable22 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("t");
//        Observable<RootSingleMeal> observable23 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("u");
//        Observable<RootSingleMeal> observable24 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("v");
//        Observable<RootSingleMeal> observable25 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("w");
//        Observable<RootSingleMeal> observable26 = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("x");
//

        ArrayList<Observable<RootSingleMeal>> arrayListObservablesRandomMeal = new ArrayList<>(Arrays.asList(observable1, observable2, observable3,
                observable4, observable5, observable6, observable7,observable8,observable11));

        Observable<RootSingleMeal> combinedObservable = Observable.merge(arrayListObservablesRandomMeal);


       // Observable<RootSingleMeal> observableCountry = RetrofitClient.getInstance().getMyApi().getRootSingleMeal("a");
        combinedObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {
                    mealsItemList = response.getMeals();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                    recyclerView.setLayoutManager(linearLayoutManager);

                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    allMealsAdapter = new AllMealsAdapter(mealsItemList);
                    recyclerView.setAdapter(allMealsAdapter);

                },
                error -> {
                    error.printStackTrace();
                }
        );

       searchTextInput .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                allMealsAdapter = new AllMealsAdapter(mealsItemList.stream().filter(
                        AreaModel ->AreaModel.getStrMeal() .startsWith(s.toString())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(allMealsAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_by_all_meals, container, false);


    }
}