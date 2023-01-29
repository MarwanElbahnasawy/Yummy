package com.example.yummy.SearchByIngredient.View;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.MainActivity.View.MainActivity;
import com.example.yummy.SearchByIngredient.Model.RootIngredientsList;
import com.example.yummy.SearchByIngredient.Model.EachIngredientModel;
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.R;
import com.example.yummy.SearchByIngredient.Presenter.InterfaceAllIngredients;
import com.example.yummy.SearchByIngredient.Presenter.PresenterAllIngredients;
import com.example.yummy.Utility.NetworkChecker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllIngredients extends Fragment implements InterfaceAllIngredients {

    private List<EachIngredientModel> ingredients;
    public static TextInputEditText textInputEditText;
    private AllIngredientsAdapter allIngredientsAdapter;
    private RecyclerView recyclerView;
    private PresenterAllIngredients presenterAllIngredients;
    private NetworkChecker networkChecker = NetworkChecker.getInstance();
    private ProgressBar progressBar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textInputEditText = view.findViewById(R.id.tinput_search_Ingediant);

        recyclerView = view.findViewById(R.id.rv_ingrediace);

        progressBar = view.findViewById(R.id.loading);
        presenterAllIngredients = new PresenterAllIngredients(this);

        presenterAllIngredients.getAllIngredients();


        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                allIngredientsAdapter = new AllIngredientsAdapter(ingredients.stream().filter(
                        AreaModel -> AreaModel.getStrIngredient().toLowerCase().startsWith(s.toString().toLowerCase())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(allIngredientsAdapter);


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

    @Override
    public void responseOfDataOnSuccess(List<EachIngredientModel> ingredientsReceived) {
        ingredients = ingredientsReceived;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        allIngredientsAdapter = new AllIngredientsAdapter(ingredients);
        recyclerView.setAdapter(allIngredientsAdapter);
        hideProgress();
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}