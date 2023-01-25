package com.example.yummy.SearchGeneral.View;

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
import com.example.yummy.Model.MealsItem;
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.R;
import com.example.yummy.SearchGeneral.Model.RootMealsFromSingleLetter;
import com.example.yummy.View.Adapters.SearchAllMealsAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchByAllMealsFragment extends Fragment {

    SearchAllMealsAdapter searchAllMealsAdapter;
    RecyclerView recyclerView;
    TextInputEditText searchTextInput;
    List<MealsItem> mealsItemList = new ArrayList<>();
    List<MealsItem> filteredMealsItemList = new ArrayList<>();
    private static final String TAG = "SearchByAllMealsFragmen";


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_allMeals);
        searchTextInput=view.findViewById(R.id.tinput_search_AllMeals);



       searchTextInput .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0){
                    mealsItemList.clear();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                    recyclerView.setLayoutManager(linearLayoutManager);

                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    searchAllMealsAdapter = new SearchAllMealsAdapter(mealsItemList);
                    recyclerView.setAdapter(searchAllMealsAdapter);
                }
                if(s.length() == 1){
                    Observable<RootMealsFromSingleLetter> observable = RetrofitClient.getInstance().getMyApi().getRootMealsBySingleLetter(s.toString());
                    observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                            response -> {

                                if (response.getMeals() != null){
                                    mealsItemList = response.getMeals();
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                                    recyclerView.setLayoutManager(linearLayoutManager);

                                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                                    searchAllMealsAdapter = new SearchAllMealsAdapter(mealsItemList);
                                    recyclerView.setAdapter(searchAllMealsAdapter);
                                }



                            },
                            error -> {
                                error.printStackTrace();
                            }
                    );

                } else if (s.length() > 1){

                    if(mealsItemList.size() != 0){
                        filteredMealsItemList.clear();
                        Observable<MealsItem> observable=Observable.fromIterable(mealsItemList);
                        observable
                                .filter
                                        (mealsItem->
                                                mealsItem.getStrMeal().toLowerCase().startsWith(searchTextInput.getText().toString().toLowerCase()))
                                .subscribe(
                                        mealsItem ->{
                                            filteredMealsItemList.add(mealsItem);

                                        }
                                        , (error) -> {}
                                        , () -> {
                                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                                            recyclerView.setLayoutManager(linearLayoutManager);
                                            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                                            searchAllMealsAdapter = new SearchAllMealsAdapter(filteredMealsItemList);
                                            recyclerView.setAdapter(searchAllMealsAdapter);
                                        }
                                );
                    }


                }


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