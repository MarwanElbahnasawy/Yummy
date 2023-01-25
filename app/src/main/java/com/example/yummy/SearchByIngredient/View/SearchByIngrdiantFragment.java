package com.example.yummy.SearchByIngredient.View;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.SearchByIngredient.Model.IngredientListModel;
import com.example.yummy.SearchByIngredient.Model.IngredientModel;
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.R;
import com.example.yummy.View.Adapters.IngredientAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchByIngrdiantFragment extends Fragment {

    List<IngredientModel> grediances;
    TextInputEditText textInputEditText;
    IngredientAdapter ingredientAdapter;
    RecyclerView recyclerView;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textInputEditText = view.findViewById(R.id.tinput_search_Ingediant);

        recyclerView = view.findViewById(R.id.rv_ingrediace);
        Observable<IngredientListModel> observableCategory = RetrofitClient.getInstance().getMyApi().getIngradiant();

        observableCategory.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {
                    grediances = response.getMeals();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                    recyclerView.setLayoutManager(linearLayoutManager);

                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    ingredientAdapter = new IngredientAdapter(grediances);
                    recyclerView.setAdapter(ingredientAdapter);
                    Log.i("eeeeee", "onViewCreated: " + grediances.get(0).getStrIngredient());
                },
                error -> {
                    error.printStackTrace();
                }
        );

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ingredientAdapter = new IngredientAdapter(grediances.stream().filter(
                        AreaModel -> AreaModel.getStrIngredient().startsWith(s.toString())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(ingredientAdapter);
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
        return inflater.inflate(R.layout.fragment_search_by_ingrdiant, container, false);
    }
}